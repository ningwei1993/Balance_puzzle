import java.util.*;

public class Main {

    static long slowPow3(int n) {
        long result = 1L;
        for (int i = 0; i < n; i++) {
            result *= 3;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        for (int i = 2; i <= 39; i++) {
            int leftTimes = i;
            long count = slowPow3(leftTimes) / 2;
            System.out.println("你的任务是 " + leftTimes + " 次之内找出 " + count + " 个小球中唯一重量不同的");
            long rightCount = 0, heavyCount = 0, lightCount = 0, otherCount = count;
            while (leftTimes > 0) {
                System.out.println("还剩 " + leftTimes + " 次使用天平机会，" + rightCount + " 个确认为重量正常，" +
                        heavyCount + " 个只可能超重，" + lightCount + " 个只可能过轻，" + otherCount + " 个不知过轻还是超重");
                System.out.println("输入四个整数分别表示放入左侧的重量正常的、只可能过超重的、只可能过轻的、其它的小球数量");
                long lr = scanner.nextLong(), lh = scanner.nextLong(), ll = scanner.nextLong(), lo = scanner.nextLong();
                System.out.println("输入四个整数分别表示放入右侧的重量正常的、只可能过超重的、只可能过轻的、其它的小球数量");
                long rr = scanner.nextLong(), rh = scanner.nextLong(), rl = scanner.nextLong(), ro = scanner.nextLong();
                if (lr < 0 || lh < 0 || ll < 0 || lo < 0 || rr < 0 || rh < 0 || rl < 0 || ro < 0) {
                    System.out.println("个数必须是非负整数");
                    continue;
                }
                if (lr + rr > rightCount || lh + rh > heavyCount || ll + rl > lightCount || lo + ro > otherCount) {
                    System.out.println("左右个数不能超过总个数");
                    continue;
                }
                leftTimes--;
                if (lr + lh + ll + lo > rr + rh + rl + ro) {
                    System.out.println("左侧更重");
                    continue;
                } else if (lr + lh + ll + lo < rr + rh + rl + ro) {
                    System.out.println("左侧更轻");
                    continue;
                }
                long equalWeight = Math.max(otherCount - lo - ro - 1 - slowPow3(leftTimes) / 2,
                        Math.max(0, heavyCount + lightCount - ll - lh - rl - rh - slowPow3(leftTimes)));
                long lightWeight = Math.max(0, ll + rh + lo + ro - slowPow3(leftTimes));
                long heavyWeight = Math.max(0, lh + rl + lo + ro - slowPow3(leftTimes));
                long sumWeight = equalWeight + lightWeight + heavyWeight;
                if (sumWeight == 0) {
                    equalWeight = Math.max(otherCount - lo - ro, heavyCount + lightCount - ll - lh - rl - rh);
                    lightWeight = ll + rh + lo + ro;
                    heavyWeight = lh + rl + lo + ro;
                    sumWeight = equalWeight + lightWeight + heavyWeight;
                }
                long randLong = Math.floorMod(random.nextLong(), sumWeight);
                if (randLong < equalWeight) {
                    System.out.println("两测重量相等");
                    otherCount -= lo + ro;
                    heavyCount -= lh + rh;
                    lightCount -= ll + rl;
                    rightCount = count - otherCount - heavyCount - lightCount;
                } else if (randLong < equalWeight + lightWeight) {
                    System.out.println("左侧更轻");
                    heavyCount = rh + ro;
                    lightCount = ll + lo;
                    otherCount = 0;
                    rightCount = count - heavyCount - lightCount;
                } else {
                    System.out.println("左侧更重");
                    heavyCount = lh + lo;
                    lightCount = rl + ro;
                    otherCount = 0;
                    rightCount = count - heavyCount - lightCount;
                }
            }
            if (count - rightCount != 1) {
                System.out.println("未知小球数为 " + (count - rightCount) + "，游戏失败！" +
                        heavyCount + " 个只可能超重，" + lightCount + " 个只可能过轻，" + otherCount + " 个不知过轻还是超重");
                return;
            }
        }
        System.out.println("恭喜通关");
    }
}