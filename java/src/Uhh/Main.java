package Uhh;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Collective> collectives = new ArrayList<>();
    public static ArrayList<Client> clients = new ArrayList<>();
    public static ArrayList<Farmer> farmers = new ArrayList<>();
    // System.out.print("\033[H\033[2J"); to clear screen


    public static void displayArray(ArrayList<?> list){
        for (Object o: list) {
            System.out.println(o.toString());
        }
        System.out.println("");
    }

    public static Client getClient(String name){
        for (Client c : clients){
            if (c.name.equals(name))
                return c;
        }
        return null;
    }

    public static Farmer getFarmer(String name){
        for (Farmer f : farmers){
            if (f.name.equals(name))
                return f;
        }
        return null;
    }

    public static Collective getCollective(String location){
        for (Collective c : collectives){
            if (c.location.equals(location))
                return c;
        }
        return null;
    }

    public static void collectiveMenu(){

    }

    public static void farmerMenu(){

    }

    public static void createUser(){
        System.out.println("Please write your name in order to register!(Size of name > 0)");
        Scanner in = new Scanner(System.in);
        String name = in.next();
        in.close();
        if(name.length() == 0){
            System.out.println("Please write a bigger name next time!");
            return;
        }
        System.out.println("Please write the type of basket you desire! (1 for small, 2 for big)");
        in = new Scanner(System.in);
        int option = in.nextInt();
        in.close();
        if (option == 1) clients.add(new Client(name, Uhh.quotes.SmallQuote.getInstance()));
        else clients.add(new Client(name, Uhh.quotes.BigQuote.getInstance()));
    }

    public static void userOptions(int option){
        System.out.println("Please write the name of the client!");
        Scanner in = new Scanner(System.in);
        String name = in.next();
        in.close();
        Client c = getClient(name);
        if(c == null){
            System.err.println("No such user " + name);
            return;
        }
        switch (option){
            case 3:
                System.out.println("3 -- Write the name of the Collective");
                in = new Scanner(System.in);
                name = in.next();
                in.close();
                Collective cl = getCollective(name);
                if(c.applyCollective(cl)) System.out.println("User added to the collective " + name);
                else System.out.println("User added to the waiting list of the " + name);
                break;
            case 4:
                c.quitCollective();
                System.out.println("User quit his collective!");
                break;
            case 5:
                if(!c.hasCollected)c.getBasket();
                else{
                    System.err.println("User already got his basket!");
                    break;
                }
                System.out.println("The user got the following basket");
                System.out.println(c.basketReceived.toString());
                break;
            case 6:
                c.cancelBasket();
                System.out.println("The user cancelled the basket for the week!");
                break;
        }
    }

    public static void userMenu(){
        System.out.print("\033[H\033[2J");
        System.out.println("Client Menu");
        System.out.println("Please select a desired function! (1/6)");
        int option;
        Scanner in;
        boolean invalid = false;
        while(!invalid){
            System.out.println("1 -- View Clients");
            System.out.println("2 -- Create Client");
            System.out.println("3 -- Apply to a Collective");
            System.out.println("4 -- Quit Collective");
            System.out.println("5 -- Get Basket");
            System.out.println("6 -- Cancel Basket");
            in = new Scanner(System.in);
            option = in.nextInt();
            in.close();
            switch (option) {
                case 1:
                    displayArray(clients);
                    break;
                case 2:
                    createUser();
                    break;
                case 3:
                    userOptions(3);
                    break;
                case 4:
                    userOptions(4);
                    break;
                case 5:
                    userOptions(5);
                    break;
                case 6:
                    userOptions(6);
                    break;
                default:
                    System.out.println("Invalid option!");
                    invalid = true;
                    break;
            }
        }
    }

    public static void main(String [] arg){
        System.out.println("Welcome to the Ugly Fruit Project!");
        System.out.println("Please select a menu! (1/3)");
        Scanner in;
        boolean invalid = false;
        while(!invalid) {
            System.out.print("\033[H\033[2J");
            System.out.println("1 -- Client Menu");
            System.out.println("2 -- Farmer Menu");
            System.out.println("3 -- Collective Menu");
            in = new Scanner(System.in);
            int option = in.nextInt();
            in.close();
            switch (option) {
                case 1:
                    userMenu();
                    break;
                case 2:
                    farmerMenu();
                    break;
                case 3:
                    collectiveMenu();
                    break;
                default:
                    System.out.println("Invalid option! Exiting Program");
                    invalid = true;
                    break;
            }
        }
    }
}
