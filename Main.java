
public class Main {
    public static void main(String args[]){
        System.out.println("Starting with creating an inventory VKR");
        int nums[][] = new int[][] {
                {1,1,1,},
                {2,2,2,},
                {3,3,3 }
        };

        for (int i = 0; i<nums.length; i++){
            for (int j = 0; j<nums.length; j++){
                System.out.print(nums[i][j]);
            }
            System.out.println();
        }
    }
}