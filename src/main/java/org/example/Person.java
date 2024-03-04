package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class Person {
    AtomicInteger id;
    AtomicInteger arrivalTime;
    AtomicInteger serviceTime;
    public Person(){
        id=new AtomicInteger(-1);
        arrivalTime=new AtomicInteger(-1);
        serviceTime=new AtomicInteger(-1);
    }
    public Person(int id,int arrivalTime,int serviceTime){
        this.id=new AtomicInteger(id);
        this.arrivalTime=new AtomicInteger(arrivalTime);
        this.serviceTime=new AtomicInteger(serviceTime);
    }
    public  boolean PersonNull(){
        return this.id.get() != -1 && this.serviceTime.get() != -1 && this.serviceTime.get() != -1;
    }
    public AtomicInteger getId() {
        return id;
    }
    public void setId(AtomicInteger id) {
        this.id = id;
    }
    public AtomicInteger getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(AtomicInteger arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public AtomicInteger getServiceTime() {
        return serviceTime;
    }
    public void setServiceTime(AtomicInteger serviceTime) {
        this.serviceTime = serviceTime;
    }
}
