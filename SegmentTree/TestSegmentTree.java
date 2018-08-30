public class TestSegmentTree{
	public static void main(String[] args){
		Integer nums[] = {1,5,9,5,-10,-1,2};
		
		//使用匿名类实现
		SegmentTree<Integer> segTree = new SegmentTree<>(nums,new Merger<Integer>(){
			public Integer merge(Integer a,Integer b){
				return a+b;
			}
		});
		
		//使用lamda表达式更简洁
		//SegmentTree<Integer> segTree = new SegmentTree<>(nums,(a,b) -> a+b);
		
		System.out.println(segTree.query(1,5));
		System.out.println(segTree.query(1,2));
		System.out.println(segTree.query(0,2));
		System.out.println(segTree.query(0,nums.length));
	}
}