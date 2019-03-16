import java.util.Arrays;
import java.util.Set;

public class DiskSpace {

    public static boolean isWritable(int blockSize, int fileSize, Set<Integer> occupiedSectors) {
        int nroOfOccupied = occupiedSectors != null ? occupiedSectors.size() : 0;
        int nroOfFree = blockSize - nroOfOccupied;

        // not using dispensable blocks to reduce stack size and increase performance
        if (nroOfOccupied == 0) return blockSize >= fileSize; // all disk free, just check if file fits
        if (fileSize > nroOfFree) return false; // there is no enough free space at all

        final Integer[] occupiedSectorsArray = new Integer[nroOfOccupied];
        occupiedSectors.toArray(occupiedSectorsArray);
        Arrays.parallelSort(occupiedSectorsArray);

        nroOfOccupied--; // using the same variable to gain performance
        if (fileSize < occupiedSectorsArray[0] || fileSize <= blockSize - occupiedSectorsArray[nroOfOccupied])
            return true; // if file fits on the beginning or ending of block

        // none of above, so need to scan for space in the middle of the block
        for (int i = 0; i < nroOfOccupied; i++)
            if (occupiedSectorsArray[i+1] - occupiedSectorsArray[i] > fileSize) return true;

        return false; // could not find free space to file
    }

}