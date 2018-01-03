package Uhh;

import org.overture.codegen.runtime.MapUtil;
import org.overture.codegen.runtime.Maplet;
import org.overture.codegen.runtime.Utils;
import org.overture.codegen.runtime.VDMMap;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Collective> collectives = new ArrayList<>();
    public static ArrayList<Client> clients = new ArrayList<>();
    public static ArrayList<Farmer> farmers = new ArrayList<>();
    public static Scanner in = new Scanner(System.in);
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

    public static void collectiveOptions(int option){
        System.out.println("Please write the name of the collective!");
        String name = in.next();
        Collective c = getCollective(name);
        if(c == null){
            System.err.println("No such collective on " + name);
            return;
        }
        switch (option){
            case 3:
                System.out.println("Write the name of the farmer you wish to add!");
                String nameF = in.next();
                Farmer f = getFarmer(nameF);
                if(f == null){
                    System.err.println("Farmer not found! ");
                    break;
                }
                if(f.hasCollective){
                    System.err.println("Farmer is already in a collective!");
                    break;
                }
                c.addFarmer(f);
                System.out.println("Farmer " + nameF + " added to the collective " + name);
                break;
            case 4:
                System.out.println("Write the name of the farmer you wish to remove!");
                String nameF2 = in.next();
                Farmer f2 = c.getFarmer(nameF2);
                if(f2 == null){
                    System.err.println("Farmer not found on this collective! ");
                    break;
                }
                c.removeFarmer(f2);
                System.out.println("Farmer " + nameF2 + " removed the collective " + name);
                break;
            case 5:
                if(c.clients.isEmpty() && c.waiting.contents.isEmpty()){
                    System.err.println("No clients on collective!");
                    break;
                }
                boolean added = false;
                if(!c.waiting.contents.isEmpty())added = c.addFromWaitingList();
                if(!c.clients.isEmpty() && !added)c.kickClients();
                c.newWeek();
                c.createBaskets();
                System.out.println("Updated baskets to collect!");
                break;
        }
    }

    public static void createCollective(){
        System.out.println("Please write the location of your collective!(Size of location > 0)");
        String name = in.next();
        if(name.length() == 0){
            System.out.println("Please write a bigger location next time!");
            return;
        }
        collectives.add(new Collective(name));
    }

    public static void collectiveMenu(){
        System.out.print("\033[H\033[2J");
        System.out.println("Collectve Menu");
        System.out.println("Please select a desired function! (1/6)");
        int option;
        boolean invalid = false;
        while(!invalid){
            System.out.println("1 -- View Collectives");
            System.out.println("2 -- Create Collective");
            System.out.println("3 -- Add Farmer");
            System.out.println("4 -- Remove Farmer");
            System.out.println("5 -- Set Up Week");
            option = in.nextInt();
            switch (option) {
                case 1:
                    displayArray(collectives);
                    break;
                case 2:
                    createCollective();
                    break;
                case 3:
                    collectiveOptions(3);
                    break;
                case 4:
                    collectiveOptions(4);
                    break;
                case 5:
                    collectiveOptions(5);
                    break;
                default:
                    System.out.println("Invalid option!");
                    invalid = true;
                    break;
            }
        }
    }

    public static void farmerOptions(int option){
        System.out.println("Please write the name of the Farmer!");
        String name = in.next();
        Farmer c = getFarmer(name);
        if(c == null){
            System.err.println("No such farmer " + name);
            return;
        }
        switch (option){
            case 3:
                System.out.println("Write the name of the Product to add and the production!");
                String nameProd = in.next();
                Number n = in.nextDouble();
                //VDMMap map1 = MapUtil.map(new Maplet(new Product(nameProd), n));
                c.addProduct(new Product(nameProd), n);
                System.out.println("Added product to production list of farmer " + name);
                break;
            case 4:
                System.out.println("Write the name of the Product to remove!");
                String nameProd1 = in.next();
                //VDMMap map1 = MapUtil.map(new Maplet(new Product(nameProd), n));
                c.removeProduct(new Product(nameProd1));
                System.out.println("Removed product to production list of farmer " + name);
                break;
        }
    }

    public static void createFarmer(){
        System.out.println("Please write your name in order to register!(Size of name > 0)");
        String name = in.next();
        if(name.length() == 0){
            System.out.println("Please write a bigger name next time!");
            return;
        }
        VDMMap map1 = MapUtil.map();
        while(true){
            System.out.println("Please write the name of the product you wish to add and the total production of it");
            System.out.println("To finish please write 'over'");
            String nameProd = in.next();
            if(nameProd.equals("over"))break;
            Number n = in.nextDouble();
            map1 = MapUtil.override(Utils.copy(map1),MapUtil.map(new Maplet(new Product(nameProd), n)));
        }
        farmers.add(new Farmer(name, map1));
    }

    public static void farmerMenu(){
        System.out.print("\033[H\033[2J");
        System.out.println("Farmer Menu");
        System.out.println("Please select a desired function! (1/5)");
        int option;
        boolean invalid = false;
        while(!invalid){
            System.out.println("1 -- View Farmers");
            System.out.println("2 -- Create Farmer");
            System.out.println("3 -- Add Product to Farmer");
            System.out.println("4 -- Remove Product from Farmer");
            System.out.println("5 -- Create Default Farmers");
            option = in.nextInt();
            switch (option) {
                case 1:
                    displayArray(farmers);
                    break;
                case 2:
                    createFarmer();
                    break;
                case 3:
                    farmerOptions(3);
                    break;
                case 4:
                    farmerOptions(4);
                    break;
                case 5:
                    VDMMap map1 = MapUtil.map(
                            new Maplet(new Product("banana"), 1000L),
                            new Maplet(new Product("apple"), 1000L),
                            new Maplet(new Product("orange"), 1000L),
                            new Maplet(new Product("pear"), 1000L),
                            new Maplet(new Product("tomato"), 1000L),
                            new Maplet(new Product("mango"), 1000L),
                            new Maplet(new Product("onion"), 1000L),
                            new Maplet(new Product("troncha"), 1000L));
                    VDMMap map2 = MapUtil.map(
                            new Maplet(new Product("banana"), 1000L),
                            new Maplet(new Product("apple"), 1000L),
                            new Maplet(new Product("orange"), 1000L),
                            new Maplet(new Product("tomato"), 1000L),
                            new Maplet(new Product("pinneapple"), 1000L),
                            new Maplet(new Product("onion"), 1000L),
                            new Maplet(new Product("troncha"), 1000L));
                    farmers.add(new Farmer("Default1", Utils.copy(map1)));
                    farmers.add(new Farmer("Default2", Utils.copy(map1)));
                    farmers.add(new Farmer("Default3", Utils.copy(map2)));
                    System.out.println("Added Default Farmers!");
                    break;
                default:
                    System.out.println("Invalid option!");
                    invalid = true;
                    break;
            }
        }
    }

    public static void createUser(){
        System.out.println("Please write your name in order to register!(Size of name > 0)");
        String name = in.next();
        if(name.length() == 0){
            System.out.println("Please write a bigger name next time!");
            return;
        }
        System.out.println("Please write the type of basket you desire! (1 for small, 2 for big)");
        int option = in.nextInt();
        if (option == 1) clients.add(new Client(name, Uhh.quotes.SmallQuote.getInstance()));
        else clients.add(new Client(name, Uhh.quotes.BigQuote.getInstance()));
    }

    public static void userOptions(int option){
        System.out.println("Please write the name of the client!");
        String name = in.next();
        Client c = getClient(name);
        if(c == null){
            System.err.println("No such user " + name);
            return;
        }
        switch (option){
            case 3:
                System.out.println("Write the name of the Collective");
                name = in.next();
                Collective cl = getCollective(name);
                if(!c.undefinedCollective){
                    System.err.println("User is already in a collective!");
                    break;
                }
                if(c.applyCollective(cl)) System.out.println("User added to the collective " + name);
                else System.out.println("User added to the waiting list of the " + name);
                break;
            case 4:
                if(!c.undefinedCollective)c.quitCollective();
                else{
                    System.err.println("User does not have a collective!");
                    break;
                }
                System.out.println("User quit his collective!");
                break;
            case 5:
                if(c.undefinedCollective){
                    System.err.println("User is not registered in a collective!");
                    break;
                }
                if(!c.hasCollected || !c.hasCancelled)c.getBasket();
                else{
                    System.err.println("User already got his basket or cancelled it!");
                    break;
                }
                System.out.println("The user got the following basket");
                System.out.println(c.basketReceived.toString());
                break;
            case 6:
                if(c.undefinedCollective){
                    System.err.println("User is not registered in a collective!");
                    break;
                }
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
        boolean invalid = false;
        while(!invalid){
            System.out.println("1 -- View Clients");
            System.out.println("2 -- Create Client");
            System.out.println("3 -- Apply to a Collective");
            System.out.println("4 -- Quit Collective");
            System.out.println("5 -- Get Basket");
            System.out.println("6 -- Cancel Basket");
            option = in.nextInt();
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
        boolean invalid = false;
        while(!invalid) {
            System.out.print("\033[H\033[2J");
            System.out.println("1 -- Client Menu");
            System.out.println("2 -- Farmer Menu");
            System.out.println("3 -- Collective Menu");
            int option = in.nextInt();
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
                    in.close();
                    break;
            }
        }
    }
}