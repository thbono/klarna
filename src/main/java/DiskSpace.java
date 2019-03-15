import java.util.Arrays;
import java.util.Set;

public class DiskSpace {

    public static boolean isWritable(int blockSize, int fileSize, Set<Integer> occupiedSectors) {
        // trusting the input will be ok and not validating it in order to achieve good performance in this critical function
        // also not using dispensable blocks
        int nroOfOccupied = occupiedSectors.size();
        if (nroOfOccupied == 0 && blockSize >= fileSize) return true;
        int nroOfFree = blockSize - nroOfOccupied;
        if (fileSize > nroOfFree) return false;

        final Integer[] occupiedSectorsArray = new Integer[nroOfOccupied];
        occupiedSectors.toArray(occupiedSectorsArray);
        Arrays.parallelSort(occupiedSectorsArray);
        nroOfOccupied--;
        for (int i = 0; i < nroOfOccupied; i++)
            if (occupiedSectorsArray[i+1] - occupiedSectorsArray[i] >= fileSize) return true;

        return true;
    }
}