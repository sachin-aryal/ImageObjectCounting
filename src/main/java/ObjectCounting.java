import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by iam on 12/3/16.
 */
public class ObjectCounting {
    static boolean labelChanged = false;
    static java.util.List<Integer> usedLabel = new ArrayList<Integer>();
    static Map<Integer,List<Integer>> parentChild = new LinkedHashMap<>();
    static int[][] matrix ;
    public static void main(String[] args) throws IOException {
        BufferedImage sourceImage = ImageIO.read(new File("grayImage.png")); // Input image that have white objects and any type of background.

        int height = sourceImage.getHeight();
        int width = sourceImage.getWidth();

        matrix = new int[width][height];

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int gray = sourceImage.getRaster().getSample(i, j, 0);

                if (gray == 255) {
                    matrix[i][j] = 1;
                } else {
                    matrix[i][j] = 0;
                }
            }
        }


        //Phase 1 labeling each pixel
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (!shouldSkip(j, width)) {
                    if (matrix[i][j] != 0) {
                        int first = getMatrixVal(i - 1, j - 1);
                        int second = getMatrixVal(i, j - 1);//top
                        int third = getMatrixVal(i + 1, j - 1);
                        int fourth = getMatrixVal(i - 1, j);//left
                        int fifth = getMatrixVal(i + 1, j);
                        int sixth = getMatrixVal(i - 1, j + 1);
                        int seventh = getMatrixVal(i, j + 1);
                        int eighth = getMatrixVal(i + 1, j + 1);

                        if (first == 0 && second == 0 && third == 0 && fourth == 0 && fifth == 0 && sixth == 0 & seventh == 0
                                & eighth == 0) {
                            matrix[i][j] = 0;
                        }
                        else{
                            matrix[i][j] = getLabel(i,j);
                        }
                    }

                }
            }
        }

        /*for(int j=0;j<height;j++){
            for(int i=0;i<width;i++){
                matrix[i][j] = replaceChildNode(i,j);
            }
            System.out.println();
        }*/

        System.out.println(usedLabel);
        System.out.println(parentChild);
        System.out.println("Total Objects in image:"+parentChild.keySet().size());
    }


    public static int replaceChildNode(int i,int j){

        int value = matrix[i][j];

        if(value == 2){
            return 2;
        }

        for(Integer key:parentChild.keySet()){
            List childList = parentChild.get(key);


        }
        return value;
    }

    public static int getLabel(int i,int j){
        int left = getMatrixVal(i-1,j);
        int top = getMatrixVal(i,j-1);

        if(usedLabel.contains(left) && usedLabel.contains(top)){
            int parent,child;
            if(top>=left){
                parent = left;
                child = top;
            }else{
                parent = top;
                child = left;
            }
            if(parentChild.containsKey(parent)){
                List childList = (List) parentChild.get(parent);
                if(!childList.contains(child)){
                    childList.add(child);
                    parentChild.put(parent,childList);
                    return parent;
                }
            }else{
                List childList;
                for(Integer key:parentChild.keySet()){
                    childList = parentChild.get(key);
                    if(childList.contains(parent)){
                        if(!childList.contains(child)){
                            childList.add(child);
                            parentChild.put(key,childList);
                        }
                        return parent;
                    }
                }
                childList = new LinkedList<>();
                childList.add(child);
                parentChild.put(parent,childList);

            }
            return parent;
        }

        if(usedLabel.contains(left)){
            return left;
        }
        if(usedLabel.contains(top)){
            return top;
        }
        if(usedLabel.size()==0){
            usedLabel.add(2);
            return 2;
        }
        int newLabel = usedLabel.get(usedLabel.size()-1)+1;
        usedLabel.add(newLabel);
        return newLabel;
    }

    public static int getMatrixVal(int x,int y){
        try{
            return matrix[x][y];
        }catch (Exception e){
            return 0;
        }
    }

    public static boolean shouldSkip(int j,int iSize){

        for(int i=0;i<iSize;i++){
            try {
                if (matrix[i][j] != 0) {
                    return false;
                }
            }catch (Exception er){
                return true;
            }
        }

        return true;
    }
}
