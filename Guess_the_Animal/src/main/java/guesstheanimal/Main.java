package guesstheanimal;


public class Main {

    public static void main(final String[] args){
        String mapperType = "json";
        if (args.length >= 2 && "-type".equals(args[0])) {
            mapperType = args[1];
        }
        new Menu(mapperType).run();
    }
}
