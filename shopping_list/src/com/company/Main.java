package com.company;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        TreeMap<String, TreeMap<String, Integer>> customers = new TreeMap<String, TreeMap<String, Integer>>();

        while(in.hasNext()) {
            String line = in.nextLine();

            String[] line_parts = line.split(" ");
            String customer_name = line_parts[0];
            String product_name = line_parts[1];
            Integer quantity = Integer.parseInt(line_parts[2]);

            if (!customers.containsKey(customer_name)) {
                customers.put(customer_name, new TreeMap<String, Integer>());
            }

            TreeMap <String, Integer> current_customer = customers.get(customer_name);
            if (!current_customer.containsKey(product_name)) {
                current_customer.put(product_name, 0);
            }

            Integer current_quantity = current_customer.get(product_name);
            current_customer.put(product_name, current_quantity + quantity);
        }

        for (Map.Entry<String, TreeMap<String, Integer>> entry : customers.entrySet()){
            String key = entry.getKey();
            TreeMap <String, Integer> value = entry.getValue();

            System.out.println(key + ":");

            for(Map.Entry<String, Integer> product : value.entrySet()) {
                String product_key = product.getKey();
                Integer product_value = product.getValue();

                System.out.println(product_key + " " + product_value);
            }
        }
    }
}
