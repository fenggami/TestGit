### Test Tools(1):Interest Calculator  ###
[https://github.com/fenggami/lawsuit-trade](https://github.com/fenggami/lawsuit-trade "calculator codes")

- **声明**
> 该小程序只是一般的利息公式计算器。不涉及企业相应商业信息，已作处理。


- **目的**
> 因为一测试任务需要验证数据的准确性，便于校验诉讼后台的利息计算以及逾期管理费等数据是否正确。而根据实际需求，逾期管理费定义是需要复利，而且涉及到利息，罚息，逾期天数，基础逾期管理费，特殊逾期管理费（分第一次，第二次）等多个需要计算的参数，所以我写了个程序小工具方便校验。

- **程序结构**
> 
>1. This is a maven project.
>2. 代码放到src/main/java中，而测试用例或单元一般放到src/test/java. 
>3. pom.xml中写的是项目用到的jar包依赖关系等。maven通过编译pom.xml自动构建项目。
>4. src/main/java/InterestCalculatorTools.java ：工具函数均放此，如利息计算器，罚息计算器，基础逾期管理费
>5. src/main/java/InterestCalculatorToolsTest.java :用来临时测试4的函数。
>6. src/main/java/InterestCalculatorUnpay.java :主要运行的java文件（条件： 截止至今日仍未还款的）
>

- **使用说明**
>
>1. 运行src/main/java/InterestCalculatorUnpay.java文件
>2. 输入框逐行输入：
>	借款日期（YYYY-MM-DD)
>	应还款日期（YYYY-MM-DD)
>	本金
>	利率
>

  ` 2016-08-01` 
  ` 2016-08-21`
  ` 200`
  ` 19`
  ` 利息为：2.18`
  ` 逾期罚息：0.91`
  ` 逾期天数：7`
  ` 应还总额（本金+利息+罚息）：203.09`
  ` 基础逾期管理费：1.4`
  ` 特殊逾期管理费：0.0`
  ` 总逾期管理费：1.4 ` 
