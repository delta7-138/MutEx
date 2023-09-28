package POR; 

import java.util.*; 
import java.io.*; 

class Request extends Transition{
    Request(MutexObject semobj)
    {
        super(semobj , new MutexObject()); 
    }

    @Override public void action()
    {
        System.out.println("Request acted upon!"); 
    }
}
