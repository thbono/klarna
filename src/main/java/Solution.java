class Solution {

    public int solution(int A, int B) {
        final int c = A*B;

        // strategy: convert throught divisions by 2, and count ones during the process
        // another way could be simply count ones from Integer.toBinaryString
        BinWrapper bin = intToBinary(c);

        return bin.nrOfOnes;
    }

    private BinWrapper intToBinary(int n) {
        final BinWrapper res = new BinWrapper();
        while (n > 0) {
            if ((n % 2 ) == 0) {
                res.bin = "0" + res.bin;
            } else {
                res.bin = "1" + res.bin;
                res.nrOfOnes++;
            }
            n = n / 2;
        }
        return res;
    }

    private class BinWrapper {
        String bin = "";
        int nrOfOnes = 0;
    }

}