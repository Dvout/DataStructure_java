public class SegmentTree<E>{
	private E data[];
	private E tree[];
	
	private Merger<E> merger;
	public SegmentTree(E[] arr, Merger<E> merger){
		this.merger = merger;
		data = (E[])new Object[arr.length];
		for(int i=0;i<arr.length;i++){
			data[i]=arr[i];
		}
		tree = (E[])new Object[4*arr.length];
		buildSegmentTree(0,0,data.length-1);
	}

	public E get(int index){
		return data[index];
	}

	public int getSize(){
		return data.length;
	}

	public int leftChild(int index){
		if(index<0 || index>=data.length)
			throw new IllegalArgumentException("Index is illegal");
		return 2*index+1;
	}

	public int rightChild(int index){
		if(index<0 || index>=data.length)
			throw new IllegalArgumentException("Index is illegal");
		return 2*index+2;
	}

	//构建线段树
	private void buildSegmentTree(int treeIndex,int l,int r){
		//if(treeIndex<0 || treeIndex>=data.length || l<0 || l>=data.length || r <0 || r>=data.length || l>r)
		//	throw new IllegalArgumentException("Index is illegal");
		//先考虑递归到底的情况，也就是l=r,到了叶子节点，直接赋值
		if(l==r){
			tree[treeIndex]=data[l];
			return;
		}
		//得到左右子树的index，以此为根节点继续构造其子树
		int leftIndex = leftChild(treeIndex);
		int rightIndex = rightChild(treeIndex);
		//为了避免r+l太大，造成整型溢出
		int mid=l+(r-l)/2;
		buildSegmentTree(leftIndex,l,mid);
		buildSegmentTree(rightIndex,mid+1,r);
		/*
		将左子树和右子树的结果merge，赋给tree[treeIndex]
		节点所存的值是其左右子树的和、最大最小值等
		由merge操作决定
		*/
		tree[treeIndex]=merger.merge(tree[leftIndex],tree[rightIndex]);
	}

	//查询某一区间merge后的值
	public E query(int queryL,int queryR){
		//判断queryL和queryR是否合理
		if(queryL<0 || queryL>=data.length || queryR<0 || queryR>=data.length || queryR<=queryL )
			throw new IllegalArgumentException("Index is illegal");
		return query(0,0,data.length-1,queryL,queryR);
	}

	private E query(int treeIndex,int l,int r,int queryL,int queryR){
		//先考虑递归到底的情况
		if(queryL==l && queryR == r){
			return tree[treeIndex];
		}
		int leftIndex = leftChild(treeIndex);
		int rightIndex = rightChild(treeIndex);
		//为了避免r+l太大，造成整型溢出
		int mid=l+(r-l)/2;
		if(l>=mid+1){
			return query(leftIndex,mid+1,r,queryL,queryR);
		}else if(r<=mid){
			return query(rightIndex,l,mid,queryL,queryR);
		}
		E left = query(leftIndex,l,mid,queryL,mid);
		E right = query(rightIndex,mid+1,r,mid+1,queryR);
		return merger.merge(left,right);
	}

	//更新某一个值，并更新其父节点的值
	public void update(int index,E val){
		if(index<0 || index>=data.length)
			throw new IllegalArgumentException("Index is illegal");
		data[index] = val;
		set(0,0,data.length-1,index,val);
	}

	private void set(int treeIndex,int l,int r,int index,E val){
		//先考虑递归到底的情况
		if(l == r){
			tree[treeIndex]=val;
			return;
		}
		int leftIndex = leftChild(treeIndex);
		int rightIndex = rightChild(treeIndex);
		//为了避免r+l太大，造成整型溢出
		int mid=l+(r-l)/2;
		if(index>=mid+1)
			set(rightIndex,mid+1,r,index,val);
		else
			set(leftIndex,l,mid,index,val);
		//更改后，需要更新一下其父节点的值
		tree[treeIndex]=merger.merge(tree[leftIndex],tree[rightIndex]);
	}

	public String toString(){
		return null;
	}
}