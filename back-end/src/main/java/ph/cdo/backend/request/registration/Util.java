package ph.cdo.backend.request.registration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Util {

    public int climbStairs(int n) {
        int counter = 0;

        for(int i = 1; i <= n ; i++){
            int ones = 1 * i;
            int twos = 2 * i;

            if(ones == n){
                counter++;
                StringBuilder combination = new StringBuilder();
                for(int y = 1; y <= ones; y++){
                    combination.append("1");
                    if(y != ones)
                        combination.append(" * ");
                }
            }

            else if(twos == n){

                counter++;
                StringBuilder combination = new StringBuilder();
                for(int y = 1; y <= ones; y++){
                    combination.append("2");
                    if(y != ones)
                        combination.append(" * ");
                }
            }
            else if(ones + twos == n){
                int result =  (factorial((ones + twos)))/ ((factorial(ones) * factorial(twos)));
                counter += result;
            }
        }
        return counter;

    }

    public int factorial(int n){
        int total = 1;
        for(int i = n ; i >= 1; i-- ){
            total *= i ;
        }
        return total;
    }
}
