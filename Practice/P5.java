import static java.util.concurrent.TimeUnit.*;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.ExecutorService;
import java.util.concurrent.*;
 
 class BeeperControl {
   private final ScheduledExecutorService scheduler =
     Executors.newScheduledThreadPool(1);

   public void beepForAnHour() {
     final Runnable beeper = new Runnable() {
       public void run() { System.out.println("beep"); }
     };

     final ScheduledFuture<?> beeperHandle =
       scheduler.scheduleAtFixedRate(beeper, 1, 1, SECONDS);
     scheduler.schedule(new Runnable() {
       public void run() { beeperHandle.cancel(true); }
     }, 60 * 60, SECONDS);
   }
 }

 public class P5 {
  public static void main(String []args){
    BeeperControl b = new BeeperControl();
    b.beepForAnHour();
  }

 }