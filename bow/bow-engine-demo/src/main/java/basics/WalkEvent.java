package basics;

public enum WalkEvent {
    walkLeft, walkRight,
    walkUp, walkDown, halt;
    public static void main(String[] args) {
        System.out.println(WalkEvent.class.getName());
    }
}
