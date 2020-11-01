class NeuralNetwork
{
  int inputNodes;
  int hiddenNodes;
  int outputNodes;
  double[][] weightsIH;
  double[][] weightsHO;
  double[][] biasesH;
  double[][] biasesO;
  static double mutationRate = 0.1;

  NeuralNetwork(int numInputNodes, int numHiddenNodes, int numOutputNodes)
  {
    inputNodes = numInputNodes;
    hiddenNodes = numHiddenNodes;
    outputNodes = numOutputNodes;
    weightsIH = new double[hiddenNodes][inputNodes];
    weightsHO = new double[outputNodes][hiddenNodes];
    Matrix.randomize(weightsIH);
    Matrix.randomize(weightsHO);
    biasesH = new double[numHiddenNodes][1]; //Don't write double[][]!!
    biasesO = new double[numOutputNodes][1];
    //Don't write double[][]!!
    Matrix.randomize(biasesH);
    Matrix.randomize(biasesO);
  }
  
  NeuralNetwork(int numInputNodes, int numHiddenNodes, int numOutputNodes, double[][] weightsIH, double[][] weightsHO, double[][] biasesH, double[][] biasesO)
  {
    inputNodes = numInputNodes;
    hiddenNodes = numHiddenNodes;
    outputNodes = numOutputNodes;
    this.weightsIH = weightsIH;
    this.weightsHO = weightsHO;
    this.biasesH = biasesH; 
    this.biasesO = biasesO;
  }

  public double[] feedForward(double[] inputArray)
  {
    double[][] inputMatrix = Matrix.fromArray(inputArray);
    double[][] hiddenOutputs = Matrix.dotMultiply(weightsIH, inputMatrix);
    //System.out.println(hiddenOutputs.length + ", "+ hiddenOutputs[0].length);
    //System.out.println(biasesH.length + ", " + biasesH[0].length);
    Matrix.elementWiseAdd(hiddenOutputs, biasesH);
    Matrix.map(new Sigmoid(), hiddenOutputs);
    double[][] output = Matrix.dotMultiply(weightsHO, hiddenOutputs);
    Matrix.elementWiseAdd(output, biasesO);
    Matrix.map(new Sigmoid(), output);
    return Matrix.toArray(output);
  }
  
  public static NeuralNetwork copy(NeuralNetwork nn)
  {
    double [][] wIH = Matrix.copy(nn.weightsIH);
    double [][] wHO = Matrix.copy(nn.weightsHO);
    double [][] bH = Matrix.copy(nn.biasesH);
    double [][] bO = Matrix.copy(nn.biasesO);
    return new NeuralNetwork(nn.inputNodes, nn.hiddenNodes, nn.outputNodes, wIH, wHO, bH, bO);
  }
  
  public void mutate1()
  {
	  //Matrix.print(weightsIH);
	  //System.out.println();
	  Matrix.map(new Mutate(), weightsIH);
	  //Matrix.print(weightsIH);
	  Matrix.map(new Mutate(), weightsHO);
	  Matrix.map(new Mutate(), biasesH);
	  Matrix.map(new Mutate(), biasesO);
  }
}