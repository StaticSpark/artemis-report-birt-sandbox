CommonTest = Base.extend({
	 constructor:function(){
		 this.base();
		 if(isTest) {
			 this.testCase();
		 }
	 }
});