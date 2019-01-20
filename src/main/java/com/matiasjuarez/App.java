package com.matiasjuarez;

import com.matiasjuarez.data.InMemoryDBManager;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        InMemoryDBManager inMemoryDBManager = InMemoryDBManager.getInstance();

        Thread.sleep(9999999);
    }
}
