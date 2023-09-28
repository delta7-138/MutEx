package POR;


abstract class Transition{
    /* 
     * Transition takes input object and returns output object
     * No action language in this case
    */
    Boolean isEnabled = false; 
    MutexObject preObj; //pre(t)
    MutexObject postObj; //post(t)

    Transition(MutexObject preObj , MutexObject postObj)
    {
        this.preObj = preObj; 
        this.postObj = postObj; 
    }

    abstract void action(); //empty for now
}