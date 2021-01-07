package behavioral.state.classic;

class State {

    void on(LightSwitch lightSwitch) {
        System.out.println("Light is already on.");
    }

    void off(LightSwitch lightSwitch) {
        System.out.println("Light is already off.");
    }
}

class OnState extends State {

    public OnState() {
        System.out.println("Light turned on.");
    }

    @Override
    void off(LightSwitch lightSwitch) {
        System.out.println("Switching light off...");
        lightSwitch.setState(new OffState());
    }
}

class OffState extends State {

    public OffState() {
        System.out.println("Light turned off.");
    }

    @Override
    void on(LightSwitch lightSwitch) {
        System.out.println("Switching light on...");
        lightSwitch.setState(new OnState());
    }
}

class LightSwitch {

    private State state; // OnState, OffState

    public LightSwitch() {
        state = new OffState();
    }

    void on() {
        state.on(this);
    }

    void off() {
        state.off(this);
    }

    public void setState(State state) {
        this.state = state;
    }
}

class ClassicImplementationGO4Demo{
    public static void main(String[] args) {
        LightSwitch lightSwitch = new LightSwitch();
        lightSwitch.on();
        lightSwitch.off();
        lightSwitch.off();
    }
}