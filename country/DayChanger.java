package country;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;



public class DayChanger extends Observable {
  private static Logger log = Logger.getLogger(DayChanger.class.getName());
    private List<Observer> observers;
    private long counterOfDays = 0;

    public DayChanger() {
        observers = new LinkedList<>();
    }

    public void runDays(int day) {
      for(int i = 1; i <=day; i++)
          runOneDay();
    }

    public void runOneDay() {
        try {
            Thread.sleep(1000);
            counterOfDays++;
            notifyObservers();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public long getCounterOfDays() {
        return counterOfDays;
    }
   
     public void notifyObservers()
    { log.log(Level.INFO, "New {0}day begin", counterOfDays);
        for (Observer observer : observers)
        {
            observer.update(this, counterOfDays);
        }
    }
    public void addObserver(Observer o) {
         observers.add(o);
         log.log(Level.INFO, "New observer added: {0}", o.getClass());
    }

    public void deleteObserver(Observer o) {
        observers.remove(o);
        log.log(Level.INFO, "observer deleted: {0}", o.getClass());
    }

   
}
