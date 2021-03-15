package behavioral.threadpool;

import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import com.google.common.collect.Sets;

public class ThreadForkJoinPool {

    public static void main(String[] args) {
        TreeNode tree = new TreeNode(5,
                new TreeNode(3), new TreeNode(2,
                new TreeNode(2), new TreeNode(8)));

        final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        final Integer sum = forkJoinPool.invoke(new CountingTask(tree));
        System.out.println(sum);
    }
}

class TreeNode {
    int value;

    Set<TreeNode> children;

    TreeNode(int value, TreeNode... children) {
        this.value = value;
        this.children = Sets.newHashSet(children);
    }
}

class CountingTask extends RecursiveTask<Integer> {

    private final TreeNode node;

    public CountingTask(TreeNode node) {
        this.node = node;
    }

    @Override
    protected Integer compute() {
        return node.value + node.children.stream().map(childNode -> new CountingTask(childNode).fork()).mapToInt(ForkJoinTask::join).sum();
    }
}