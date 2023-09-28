package POR; 

import java.util.*; 

class MutexObject {
    String name; 
    Boolean val = true; // possible value true is for open and false is for closed
    Boolean isEmpty = false;

    MutexObject()
    {
        this.name = "empty object"; 
        this.val = true; 
        this.isEmpty = true; 
    }

    MutexObject(String name)
    {
        this.name = name; 
    }

    MutexObject(String name , Boolean val)
    {
        this.name = name; 
        this.val = val; 
    }
}
