/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simul.project;

import java.util.Scanner;

/**
 *
 * @author FM
 */
public class SimulProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
                //firstTable A = new firstTable();
                //A.setVisible(true);
                
                        
   		float[][] a = new float[50][50];
		float y, sum = 0;
		int x;
		System.out.print("please enter number of simulate daily demands :");
		x = STDIN_SCANNER.nextInt();

		for(int i = 0; i < x; i++) {
			a[i][0] = i; //daily demand
		}

		System.out.print("please enter the frequency (days) : ");
		for(int i = 0; i < x; i++) {
			a[i][1] = STDIN_SCANNER.nextFloat(); //frequency
		}
		System.out.println("\n------------------------1. demand table----------------");
		for(int i = 0; i < x; i++) {
			sum += a[i][1];
		}
		for(int i = 0; i < x; i++) {
			a[i][2] = a[i][1] / sum; //probability
		}
		for(int i = 0; i < x; i++) {
			if(i == 0) {
				a[i][3] = a[i][2];
			} else {
				a[i][3] = a[i][2] + a[i - 1][3]; //cumulative
			}
		}
		for(int i = 0; i < x; i++) {
			if(i == 0) {
				a[i][4] = 1;
				a[i][5] = a[i][3] * 100;
			} else {
				a[i][4] = a[i - 1][5] + 1;
				a[i][5] = a[i][3] * 100;
			}
		}
		System.out.println("demand\t\tfrequency\tprobability\tcumulative\tinterval from  to");
		for(int i = 0; i < x; i++) {
			for(int z = 0; z < x; z++) {
				System.out.printf("%.2f\t\t", a[i][z]);
			}
			System.out.println();
		}
                //second table
                		float[][] lt = new float[50][50];
		int l;
		float summ = 0;
		System.out.print("Enter a number of lead time : ");
		l = STDIN_SCANNER.nextInt();
		for(int i = 0; i < l; i++) {
			lt[i][0] = i + 1;
		}
		System.out.println("Enter the frequency (order) :");
		for(int i = 0; i < l; i++) {
			lt[i][1] = STDIN_SCANNER.nextFloat();
		}
		System.out.println("\n--------------------------- 2. Lead time ---------------------------------");
		for(int i = 0; i < l; i++) {
			summ += lt[i][1];
		}
		for(int i = 0; i < l; i++) {
			lt[i][2] = lt[i][1] / summ;
		}
		for(int i = 0; i < l; i++) {
			if(i == 0) {
				lt[i][3] = lt[i][2];
			} else {
				lt[i][3] = lt[i][2] + lt[i - 1][3];
			}
		}
		for(int i = 0; i < l; i++) {
			if(i == 0) {
				lt[i][4] = 1;
				lt[i][5] = lt[i][3] * 100;
			} else {
				lt[i][4] = lt[i - 1][5] + 1;
				lt[i][5] = lt[i][3] * 100;
			}
		}
		System.out.println("demand\t\tfrequency\tprobability\tcumulative\tinterval from  to");
		for(int i = 0; i < l; i++) {
			for(int k = 0; k < 6; k++) {
				System.out.printf("%.2f\t\t", lt[i][k]);
			}
			System.out.println();
		}
                //third table
                	
		int[] demand = new int[50];
		int[] start = new int[50];
		int[] end = new int[50];
		for(int i = 0; i < x; i++) {
			demand[i] = (int)a[i][0];
			start[i] = (int)a[i][4];
			end[i] = (int)a[i][5];
		}
		float[][] inventory = new float[50][10];
		int j, s, m;
		float reorder, startquantity, orderquantity, count = 0.0f, startCounter = 0.0f;
		float sumEndingInv = 0.0f, sumLostSales = 0.0f, sumOrderPlaced = 0.0f;
		float avgEndingInv = 0, avgLostSales = 0, avgOrederPlaced = 0;
		System.out.print("please enter the number of days you want to be simulated : ");
		j = STDIN_SCANNER.nextInt();
		System.out.print("enter quantity number=");
		orderquantity = STDIN_SCANNER.nextFloat();
		System.out.print("enter the start quantity =");
		startquantity = STDIN_SCANNER.nextFloat();
		System.out.print("enter reorder point=");
		reorder = STDIN_SCANNER.nextFloat();
    
                for(s = 0; s < j; s++) {
			if(startCounter == 0) {
				startCounter = -1;
			}
                        if(startCounter > 0) {
				startCounter--;
			}
			inventory[s][0] = s + 1; //day
                        if(s == 0) {
                            inventory[s][1] = 0; //unit receive
				inventory[s][2] = startquantity; //beginning inventory/
			} else {
				if(startCounter == 0) {
					inventory[s][1] = orderquantity; //unit receive/
				} else {
					inventory[s][1] = 0; //count=0;/
				}
				inventory[s][2] = inventory[s - 1][5] + inventory[s][1];
			}
                
                System.out.print("enter a rondom number : ");
                    
			inventory[s][3] = STDIN_SCANNER.nextFloat();
                        for(int i = 0; i < x; i++) {
				if(start[i] <= inventory[s][3] && inventory[s][3] <= end[i]) {
					inventory[s][4] = demand[i]; //demand/
				}
			}
                        if(inventory[s][4] <= inventory[s][2]) {
				inventory[s][5] = inventory[s][2] - inventory[s][4];
				inventory[s][6] = 0; //lostSales/
			} else {
				inventory[s][5] = 0;
				inventory[s][6] = inventory[s][4] - inventory[s][2];
			}
                        
			if(inventory[s][5] <= reorder && startCounter < 0) {
			inventory[s][7] = 1;
		} else {
			inventory[s][7] = 0;
		}
                        if(inventory[s][7] == 1) {
			System.out.print("enter the random number to lead time");
			inventory[s][8] = STDIN_SCANNER.nextFloat();
			for(int i = 0; i < l; i++) {
				if(lt[i][4] <= inventory[s][8] && inventory[s][8] <= lt[i][5]) {
					inventory[s][9] = lt[i][0];
				}
			}
                        System.out.printf("%f", inventory[s][9]);
			startCounter = inventory[s][9] + 1;
		} else {
			inventory[s][8] = 0;
			inventory[s][9] = 0;
		}
                        sumEndingInv = sumEndingInv + inventory[s][5];
		avgEndingInv = sumEndingInv / j;

		sumLostSales = sumLostSales + inventory[s][6];
		avgLostSales = sumLostSales / j;
                sumOrderPlaced = sumOrderPlaced + inventory[s][7];
		avgOrederPlaced = sumOrderPlaced / j;
    }
                System.out.println("\n--------------------------- ENVENTORY TABLE ---------------------------------\n");
                System.out.println("day   Recived  Biginnig\tRondom1\tDemand\tEnding   Lost  Order  Rondom2  LeadTime\n ");
		for( s = 0; s < j; s++) {
			for( m = 0; m < 10; m++) {
				System.out.printf("%.2f\t", inventory[s][m]);
			}
                   System.out.printf("\n");     
		}
                System.out.printf("\n");
                System.out.printf("\n");
                
                System.out.printf("the average ending inventory:  is%.2f", avgEndingInv);
                System.out.printf("\n");
                System.out.printf("the average lost sales is:  %.2f", avgLostSales);
                System.out.printf("\n");
		System.out.printf("the average order placed is:%.2f ", avgLostSales);
                System.out.printf("\n");
                System.out.printf("\n");
                
                
                int numOfDays;
		float orderingCost, holdingCost, lostSalesCost, cOneUnit;
		float dOrderCost, dHoldingCost, dStockOut, diNvCost;
		System.out.print("enter the number of days you work of the year :");
		numOfDays = STDIN_SCANNER.nextInt();
		System.out.println();
		System.out.print("enter the cost of placing one order :");
		orderingCost = STDIN_SCANNER.nextFloat();
		System.out.println();
		System.out.print("enter the holding cost per drill per year :");
		holdingCost = STDIN_SCANNER.nextFloat();
		System.out.println();
		System.out.print("enter the cost per lost sale :");
		lostSalesCost = STDIN_SCANNER.nextFloat();
		System.out.println();
		cOneUnit = holdingCost / numOfDays;
		dOrderCost = orderingCost * avgOrederPlaced;
		System.out.printf("the daily order cost is  %.2f", dOrderCost);
		System.out.println();
		System.out.println();
		dHoldingCost = cOneUnit * avgEndingInv;
		System.out.printf("the daily holding cost is  %.2f", dHoldingCost);
		System.out.println();
		System.out.println();
		dStockOut = lostSalesCost * avgLostSales;
		System.out.printf("the daily stockout cost is  %.2f", dStockOut);
                System.out.println();
		System.out.println();
		diNvCost = dOrderCost + dHoldingCost + dStockOut;
		System.out.printf("total daily inventory cost is  %.2f", diNvCost);
	         System.out.println();
		System.out.println();
                      
    }       
                
	public final static Scanner STDIN_SCANNER = new Scanner(System.in);
}

