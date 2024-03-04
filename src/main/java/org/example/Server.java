package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private ArrayBlockingQueue<Person> waitList;
    private ArrayBlockingQueue<AtomicInteger> clientOnThread;
    private ArrayBlockingQueue<MyThread> queue;
    private final int nrClient;
    private final int minArrival;
    private final int maxArrival;
    private final int maxService;
    private final int minService;
    public volatile AtomicInteger time=new AtomicInteger(0);
    private volatile String output="";
    public final int sleepTime=1000;
    private String[] columnName = {"Queue id", "Client", "Queue's waiting List"};
    private String[] threadName;
    private ArrayBlockingQueue<String> clientToShow;
    private ArrayBlockingQueue<String> threadWaitingList;

    public Server(int simulationTime, int nrQueues , int nrPersons, int arrivalMin, int arrivalMax, int serviceMin, int serviceMax,View view){
        this.waitList= new ArrayBlockingQueue<Person>(nrPersons);
        this.clientOnThread= new ArrayBlockingQueue<AtomicInteger>(nrQueues);
        this.nrClient=nrPersons;
        this.minArrival=arrivalMin;
        this.maxArrival=arrivalMax;
        this.maxService=serviceMax;
        this.minService=serviceMin;
        this.queue= new ArrayBlockingQueue<MyThread>(nrQueues);
        this.threadName=new String[nrQueues];
        for(int i=1; i<=nrClient; i++){
            Person client = ClientGenerator(i);
            waitList.add(client);
        }
        //printWaitList();
        bubbleSortByArrivalTime();
        printWaitList();
        //TODO: make the Thread things because you already have the clients in a sorted manner (by arrival time)

       for(int i = 0; i< nrQueues; i++){
            org.example.MyThread auxThread= new org.example.MyThread(i,sleepTime,waitList,time, simulationTime,clientOnThread,nrQueues,view,this);
            auxThread.start();
            queue.add(auxThread);
            threadName[i]="Queue "+i+":";
       }
        for(; time.get()< simulationTime; time.getAndIncrement()) {
            // TODO Here sleeps the "twinkle twinkle little star"
            System.out.println("Time is:" + time.get());
            synchronized (output){
                output=output+ "Time is:" + String.valueOf(time)+'\n';
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
        }
        try {
            stopAllThreads();
        }
        catch (Exception e){}

    }

    public void updateTable(int id, String Text, Person person){

    }
    public void addText(String text){
        output= output+ text;
    }
    private Person ClientGenerator(int id) {
        Random rand = new Random();
        int serviceTime = rand.nextInt(maxService - minService + 1) + minService;
        int arrivalTime = rand.nextInt(maxArrival - minArrival + 1) + minArrival;
        return new Person(id,serviceTime,arrivalTime);
    }
    public void bubbleSortByArrivalTime() {
        List<Person> persons = new ArrayList<>(waitList);
        waitList.clear();
        for (int i = 0; i < nrClient-1; i++) {
            boolean ok=true;
            for (int j = 0; j < nrClient-1; j++) {
                Person currPerson = persons.get(j);
                Person nextPerson = persons.get(j + 1);
                if (currPerson.getArrivalTime().get() > nextPerson.getArrivalTime().get()) {
                    persons.set(j, nextPerson);
                    persons.set(j + 1, currPerson);
                    ok=false;
                }
                currPerson=nextPerson;
                nextPerson= persons.get(j+1);
            }
            if(ok) break;
        }
        waitList = new ArrayBlockingQueue<Person>(nrClient);
        waitList.addAll(persons);
    }
    public void printWaitList() {
        String text="Wait list: ";
        for (Person person : waitList) {
            text= text+"(" + person.getId().get() + "," + person.getArrivalTime().get() + "," + person.getServiceTime().get() + ") ";
        }
        addText(text+'\n');
        System.out.println(text);
    }
    public ArrayBlockingQueue<Person> getWaitList() {
        return waitList;
    }
    public void setWaitList(ArrayBlockingQueue<Person> waitList) {
        this.waitList = waitList;
    }
    public AtomicInteger getTime() {
        return time;
    }
    public void setTime(AtomicInteger time) {
        this.time = time;
    }
    public static void writeToFile(String str, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(str);
        writer.close();
    }
    public void stopAllThreads() throws InterruptedException {
        for (MyThread thread : queue) {
            if(thread.isAlive()) {
                thread.join();
            }
        }
        try {
            writeToFile(output,"output.txt");
        }catch (Exception e){}
        waitList.clear();
        clientOnThread.clear();
        queue.clear();
    }
}
