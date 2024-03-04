package org.example;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThread extends Thread{
    private AtomicInteger id;
    private Person person= new Person();
    private final int sleepTime;
    private ArrayBlockingQueue<Person> waitList;
    private ArrayBlockingQueue<Person> myWaitList;
    private ArrayBlockingQueue<AtomicInteger> clientOnThread;
    public volatile AtomicInteger time;
    private final int simTime;
    private boolean stopRunning=false;
    private boolean needPerson=true;
    private  int personService;
    private int timerWhenServed=0;
    private View view;
    private volatile Server server;
    public MyThread(int id,int sleepTime,ArrayBlockingQueue<Person> waitList,AtomicInteger time,int simTime,ArrayBlockingQueue<AtomicInteger> clientOnThread,int nrQueues,View view,Server serverul){
        this.id = new AtomicInteger(id);
        if(this.id.get()==0)this.view = view;
        this.sleepTime=sleepTime+1;
        this.waitList=waitList;
        this.time=time;
        this.simTime=simTime;
        this.clientOnThread= clientOnThread;
        this.myWaitList= new ArrayBlockingQueue<Person>((waitList.size()/(nrQueues))+nrQueues+1);
        if(!this.waitList.isEmpty()){
            myWaitList.add(this.waitList.poll());
        }
        this.server=serverul;
        allPrint("Thread created successfully: "+id+'\n');
    }
    @Override
    public void run(){
        while(!stopRunning) {
            iGetClient();
            if (person == null || time.get() >= simTime) {
                allPrint("Stopped Thread" + id);
                stopMyThread();
            } else {
                if (person.PersonNull()) {
                    //TODO: STUFF HERE with the person
                        person.serviceTime.set(personService - time.get()+timerWhenServed);
                        allPrint("Queue:" + id + " has person: " + person.getId() + " (" + person.getArrivalTime() + "," + person.getServiceTime() + ")");
                    if (person.serviceTime.get() <= 0) {
                        //System.out.println("Queue:" + id + " person "+person.id.get()+ " done" );
                        needPerson = true;
                        iGetClient();
                        if(person.PersonNull()){
                            allPrint("Queue:" + id + " has person: " + person.getId() + " (" + person.getArrivalTime() + "," + person.getServiceTime() + ")");
                        }
                        //else allPrint(" NEW person but is null");
                    }
                } else {
                    //allPrint("Thread " + id + " trage chiulu");
                    needPerson = true;
                }

            }
            // Here sleeps the "twinkle twinkle little star"
            if(id.get()==0)  {
                view.setActualTime(String.valueOf(time));
                view.revalidate();
            }
            try {
                Thread.sleep(sleepTime+20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);

            }
        }
    }
    public void allPrint(String text){
        server.addText(text+'\n');
        System.out.println(text);
    }
    private void iGetClient(){
        try {
            whoGetClient();
        } catch (InterruptedException e) {
        }
        if (needPerson && person.getServiceTime().get() <= 0) {
            if (!myWaitList.isEmpty()) {
                person = myWaitList.peek();
                if (person.arrivalTime.get() > time.get()) {
                    personService=0;
                    timerWhenServed=time.get();
                    person = new Person();
                } else {
                    myWaitList.poll();
                    personService=person.serviceTime.get();
                    timerWhenServed=time.get();
                    needPerson = false;
                    //System.out.println(id+" got a person");
                }
            } else {
                person = new Person();
                timerWhenServed=time.get();
                personService=0;
            }
        }
    }
    private void whoGetClient() throws InterruptedException {
       // System.out.println("Thread "+id+" accessed whoGetClient");
        AtomicInteger smallestClient = new AtomicInteger();
        AtomicInteger index = new AtomicInteger(0);
        AtomicInteger save = new AtomicInteger(0);
        synchronized (clientOnThread){
            if(!clientOnThread.isEmpty()) {
                smallestClient.set(clientOnThread.peek().get());
                clientOnThread.forEach(client -> {
                    if (client.get() < smallestClient.get()) {
                        smallestClient.set(client.get());
                        save.set(index.get());
                    }
                    index.getAndIncrement();
                });
            }
        }
        if(save.get()==id.get()){
            index.set(0);
            Person aux = waitList.poll();
            if(aux!=null) {
                myWaitList.put(aux);
            }
            else return;
            synchronized (clientOnThread) {
                clientOnThread.forEach(client -> {
                     if (index.get()==save.get()) {
                        client.getAndIncrement();
                        return;
                     }
                     index.getAndIncrement();
                });
            }
        }
    }
    public void stopMyThread(){
        stopRunning=true;
    }
}

