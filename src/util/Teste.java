package util;



/*
 * Classe PERCEPTRON responsável para aprendizado e resolução da tabela AND
 *
 * @author Dimas Kastibergue <k45t1b@gmail.com>;
 */
 
public class Teste {
 
    // pesos sinápticos [0] entrada 1, [1] entrada 2, [3]BIAS
    private double[] w = new double[3];
 
    // variável responsável pelo somatório(rede).
    private double NET = 0;
 
    // variavél responsável pelo número máximo de épocas
    private final int epocasMax = 30;
 
    // variável responsável pela contagen das épocas durante o treinamento
    private int count = 0;
 
    // declara o vetor da matriz de aprendizado
    private int[][] matrizAprendizado = new int[4][3];
 
    // MÉTODO DE RETORNO DO CONTADOR
    public int getCount(){
 
      return this.count;
 
    }
 // metodo de inicialização inicia o vetor da matriz de aprendizado
  Teste() {
 
    // Primeiro valor
    this.matrizAprendizado[0][0] = 0; // entrada 1
    this.matrizAprendizado[0][1] = 0; // entrada 2
    this.matrizAprendizado[0][2] = 0; // valor esperado
 
    // Segundo Valor
    this.matrizAprendizado[1][0] = 0; // entrada 1
    this.matrizAprendizado[1][1] = 1; // entrada 2
    this.matrizAprendizado[1][2] = 0; // valor esperado
 
    // terceiro valor
    this.matrizAprendizado[2][0] = 1; // entrada 1
    this.matrizAprendizado[2][1] = 0; // entrada 2
    this.matrizAprendizado[2][2] = 0; // valor esperado
 
    // quarto valor
    this.matrizAprendizado[3][0] = 1; // entrada 1
    this.matrizAprendizado[3][1] = 1; // entrada 2
    this.matrizAprendizado[3][2] = 1; // valor esperado
 
    // inicialização dos pesos sinápticos
 
    // Peso sináptico para primeira entrada.
    w[0] = 0;
    // Peso sináptico para segunda entrada.
    w[1] = 0;
    // Peso sináptico para o BIAS
    w[2]= 0;
 
}
 
  // Método responsávelpelo somatório e a função de ativação.
    int executar(int x1, int x2) {
 
        // Somatório (NET)
        NET = (x1 * w[0]) + (x2 * w[1]) + ((-1) * w[2]);
 
        // Função de Ativação
        if (NET > 0) {
            return 1;
        }
        return 0;
    }
 
    // Método para treinamento da rede
    public void treinar() {
 
        // variavel utilizada responsável pelo controlede treinamento recebefalso
        boolean treinou= true;
        // varável responsável para receber o valor da saída (y)
        int saida;
 
        // laço usado para fazer todas as entradas
        for (int i = 0; i < 4; i++) {
            // A saída recebe o resultado da rede que no caso é 1 ou 0
            saida = executar(matrizAprendizado[i][0], matrizAprendizado[i][1]);
 
 
            if (saida != matrizAprendizado[i][2]) {
                // Caso a saída seja diferente do valor esperado
 
                // os pesos sinápticos serão corrigidos
                corrigirPeso(i, saida);
                // a variavél responsável pelo controlede treinamento recebe falso
                treinou = false;
 
            }
        }
        // acrescenta uma época
        this.count++;
 
        // teste se houve algum erro duranteo treinamento e o número de epocas
        //é menor qe o definido
        if((treinou == false) && (this.count < this.epocasMax)) {
            // chamada recursiva do método
            treinar();
 
        }
 
    }    // fim do método para treinamento
 
    // Método para a correção de pesos
    void corrigirPeso(int i, int saida) {
 
        w[0] = w[0] + (1 * (matrizAprendizado[i][2] - saida) * matrizAprendizado[i][0]);
        w[1] = w[1] + (1 * (matrizAprendizado[i][2] - saida) * matrizAprendizado[i][1]);
        w[2] = w[2] + (1 * (matrizAprendizado[i][2] - saida) * (-1));
 
    }
 
    void testar() {
 
 
        System.out.println(" Teste 01 para 0 e 0 " + executar(0, 0));
 
        System.out.println(" Teste 02 para 0 e 1 " + executar(0, 1));
 
        System.out.println(" Teste 03 para 1 e 0 " + executar(1, 0));
 
        System.out.println(" Teste 04 para 0 e 0 " + executar(0, 0));
 
        System.out.println(" Teste 05 para 1 e 1 " + executar(1, 1));
 
    }
 
    public static void main(String[] arguments) {
 
    	Teste p = new Teste();
 
        p.treinar();
 
        System.out.println("Para aprender o algoritmo treinou " + p.getCount() + " epocas! \n ");
 
        p.testar();
 
    }
}



















//package util;
//
//import java.io.FileNotFoundException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//import javax.swing.JOptionPane;
//
//import core.Item;
//import core.Rating;
//import core.User;
//import enums.TypeMatrix;
//
//public class LoaderClassicMatrix extends AbstractLoad {
//	// This cooresponds to the configuration Map<User, Map<Item, Rating>>
//	// matrix;
//	Map<Integer, Map<Integer, Double>> matrix;
//
//	public LoaderClassicMatrix(String pathName, TypeMatrix type) {
//		super(pathName, type);
//		this.matrix = new HashMap<Integer, Map<Integer, Double>>();
//	}



//
//	/**
//	 * This method receive a array of values and mount the matrix
//	 * 
//	 * @param arrayOfValues
//	 */
//
//	private void loadMatrix(String[] arrayOfValues) {
//
//		Item item = new Item(Integer.parseInt(arrayOfValues[1]));
//		Rating rating = new Rating();
//		rating.setRating(Double.parseDouble(arrayOfValues[2]));
//		rating.setIdItem(item.getId());
//		User user = new User(Integer.valueOf(arrayOfValues[0]));
//		rating.setIdUser(user.getId());
//
//		int idUser = user.getId();
//		int idItem = item.getId();
//		double value = rating.getRating();
//		if (!matrix.containsKey(idUser)) {
//			Map<Integer, Double> map = new HashMap<Integer, Double>();
//			map.put(idItem, value);
//			matrix.put(idUser, map);
//		} else {
//			Map<Integer, Double> map = matrix.get(idUser);
//			if (map.containsKey(idItem))
//				throw new IllegalArgumentException("This item " + item.getId()
//						+ " already had been rated by the user! ");
//			else {
//				map.put(idItem, value);
//				matrix.remove(idUser);
//				matrix.put(idUser, map);
//			}
//		}
//	}
//
//	/**
//	 * From a formatted dataset, this method returns the matrix with all values
//	 * for the user' ratings about items
//	 * 
//	 * @return matrix
//	 */
//	@Override
//	public Map<Integer, Map<Integer, Double>> getData() {
//
//		try {
//			Scanner scanner = new Scanner(this.getDataset());
//			while (scanner.hasNextLine())
//				this.loadMatrix(scanner.nextLine().split("[.^,]"));
//
//			return matrix;
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "FileNotFoundException");
//
//			return null;
//		} catch (NumberFormatException e) {
//			System.out.println(e.getMessage());
//			JOptionPane.showMessageDialog(null, "NumberFormatException");
//			throw new NumberFormatException("NumberFormatException");
//		}
//	}
//}