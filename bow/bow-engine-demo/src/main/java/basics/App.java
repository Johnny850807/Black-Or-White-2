package basics;

import com.pokewords.framework.views.GameApplication;

public class App extends GameApplication {
    public App() {
        setWindowLocation(200, 200);
        setWindowName("My Game");
        setWindowSize(200, 200);
    }

    public void onAppInit() {

    }

    public void onAppLoading() {

    }

    public void onAppStarted() {

    }

    public static void main(String[] args) {
        System.out.println("123");
        new App().launch();
    }
}
