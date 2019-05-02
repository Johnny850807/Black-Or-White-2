package basics;

import com.pokewords.framework.views.GameApplication;

public class App extends GameApplication {

    public void onAppInit() {
        setWindowLocation(750, 320);
        setWindowName("Basic Game Demo");
        setWindowSize(1000, 650);
    }


    public void onAppStarted() {

    }

    public static void main(String[] args) {
        new App().launch();
    }
}
