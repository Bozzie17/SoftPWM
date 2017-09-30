import com.pi4j.io.gpio.*;
import com.pi4j.util.Console;

/**
 * Created by s213227304 on 2017/09/29.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        // ####################################################################
        //
        // since we are not using the default Raspberry Pi platform, we should
        // explicitly assign the platform as the BananaPi platform.
        //
        // ####################################################################

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate code)
        final Console console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "SoftPWM Example (Software-driven PWM Emulation)");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // ####################################################################
        //
        // When provisioning a pin, use the BananaPiPin class.
        //
        // ####################################################################

        // by default we will use gpio pin #01; however, if an argument
        // has been provided, then lookup the pin by address
        Pin pin = RaspiPin.GPIO_01;               // argument array to search in

        // we will provision the pin as a software emulated PWM output
        // pins that support hardware PWM should be provisioned as normal PWM outputs
        // each software emulated PWM pin does consume additional overhead in
        // terms of CPU usage.
        //
        // Software emulated PWM pins support a range between 0 (off) and 100 (max) by default.
        //
        // Please see: http://wiringpi.com/reference/software-pwm-library/
        // for more details on software emulated PWM
        GpioPinPwmOutput pwm = gpio.provisionSoftPwmOutputPin(pin);

        // optionally set the PWM range (100 is default range)
        pwm.setPwmRange(100);

        // prompt user that we are ready
        console.println(" ... Successfully provisioned PWM pin: " + pwm.toString());
        console.emptyLine();

        // set the PWM rate to 100 (FULLY ON)
        pwm.setPwm(100);
        console.println("Software emulated PWM rate is: " + pwm.getPwm());

        console.println("Press ENTER to set the PWM to a rate of 50");
        System.console().readLine();

        // set the PWM rate to 50 (1/2 DUTY CYCLE)
        pwm.setPwm(50);
        console.println("Software emulated PWM rate is: " + pwm.getPwm());

        console.println("Press ENTER to set the PWM to a rate to 0 (stop PWM)");
        System.console().readLine();

        // set the PWM rate to 0 (FULLY OFF)
        pwm.setPwm(0);
        console.println("Software emulated PWM rate is: " + pwm.getPwm());

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        gpio.shutdown();
    }
}
