import java.lang.Math;

class Matrix 
{
  static void print (double[][] matrix)
	{
		for (int i = 0; i < matrix.length; i++)
		{
			System.out.print("{ ");
			for (int j = 0; j < matrix[i].length; j++)
			{
        if (j == matrix[i].length - 1)
          System.out.print(matrix[i][j] + " }");
        else
				  System.out.print(matrix[i][j] + ", ");
			}
			System.out.println();
		}
	}
  
  static double[][] copy (double[][] matrix)
  {
    double[][] newMatrix = new double[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix[i].length; j++)
			{
        newMatrix[i][j] = matrix[i][j];
      }
    } 
    return newMatrix;
  }

  static void randomize (double[][] matrix)
  {
    for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix[i].length; j++)
			{
        matrix[i][j] = Math.random() * 2 - 1;
      }
    }
  }

  static double[][] fromArray (double[] arr)
  {
    double[][] matrix = new double[arr.length][1]; 
    for (int i = 0; i < arr.length; i++)
    {
      matrix[i][0] = arr[i];
    }
    return matrix;
  }

  static double[] toArray (double[][] matrix)
  {
    double[] arr = new double[matrix.length*matrix[0].length];
    for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix[i].length; j++)
			{
				arr[matrix[0].length*i + j] = matrix[i][j];
			}
		}
    return arr;
  }

  static void scalorAdd (double[][] matrix, int n)
  {
    for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix[i].length; j++)
			{
				matrix[i][j] += n;
			}
		} 
  }

  static void scalorMultiply (double[][] matrix, int n)
  {
    for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix[i].length; j++)
			{
        matrix[i][j] *= n;
      }
    } 
  }

  static double[][] elementWiseAdd(double[][] m1, double[][] m2)
  {
    if (m1.length == m2.length && m1[0].length == m2[0].length)
    {
      double[][] newMatrix = new double[m1.length][m1[0].length];
      for (int i = 0; i < newMatrix.length; i++)
		  {
			  for (int j = 0; j < newMatrix[i].length; j++)
			  {
          newMatrix[i][j] = m1[i][j] + m2[i][j];
        }
      }
      return newMatrix;
    }
   else
    return null;
  }

  static double[][] elementWiseMultiply(double[][] m1, double[][] m2)
  {
    if (m1.length == m2.length && m1[0].length == m2[0].length)
    {
      double[][] newMatrix = new double[m1.length][m1[0].length];
      for (int i = 0; i < newMatrix.length; i++)
		  {
			  for (int j = 0; j < newMatrix[i].length; j++)
			  {
          newMatrix[i][j] = m1[i][j] * m2[i][j];
        }
      }
      return newMatrix;
    }
   else
    return null;
  }

  static double[][] dotMultiply(double[][] m1, double[][] m2)
  {
    if (m1[0].length == m2.length)
    {
      double[][] newMatrix = new double[m1.length][m2[0].length];
      for (int i = 0; i < newMatrix.length; i++)
		  {
			  for (int j = 0; j < newMatrix[i].length; j++)
			  {
          //newMatrix[i][j] = 0.0;
          for (int a = 0; a < m1[0].length; a++)
          {
            //Complicated, refer to video
            newMatrix[i][j] += m1[i][a] * m2[a][j];
          }
        }
      }
      return newMatrix;
    }
   else
    return null;
  }

  static double[][] transpose (double[][] matrix)
  {
    double[][] newMatrix = new double[matrix[0].length][matrix.length];
    for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix[i].length; j++)
			{
        newMatrix[j][i] = matrix[i][j];
      }
    } 
    return newMatrix;
  }

  static void map (Command command, double[][] matrix)
  {
    for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix[i].length; j++)
			{
        matrix[i][j] = command.execute(matrix[i][j]);
      }
    }
  }
}