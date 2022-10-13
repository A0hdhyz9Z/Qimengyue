import java.util.Arrays;
import java.util.List;

public class LogRegression {
    public static void main(String[] args) {
        LogRegression lr = new LogRegression();
        ReadData instances = new ReadData();
        lr.train(instances, 0.001f, 2); //
    }

    public void train(ReadData instances, float step, int type) {
        List<List<Float>> datas = ReadData.dataList;
        List<Float> labels = ReadData.labelList;
        int size = datas.size();
//        System.out.println("size: " + size);
        int dim = datas.get(0).size();
//        System.out.println("dim: " + dim);
        float[] w = new float[dim]; // 初始化权重
        float changas = Float.MAX_VALUE;
        int caculate = 0;

        switch (type) {
            case 1: // 批梯度下降的方式
                while (changas > 0.0001) {
                    float[] wClone = w.clone();
                    float[] out = new float[size];
                    for (int s = 0; s < size; s++) {
                        float lire = innerProduct(w, datas.get(s));
                        out[s] = sigmoid(lire);
                    }
                    for (int d = 0; d < dim; d++) {
                        float sum = 0;
                        for (int s = 0; s < size; s++) {
                            sum += (labels.get(s) - out[s]) * datas.get(s).get(d);
                        }
                        float q=w[d];
                        w[d] = (float) (q + step * sum);

//					w[d] = (float) (q + step * sum-0.01*Math.pow(q,2)); L2正则
//					w[d] = (float) (q + step * sum-0.01*Math.abs(q));  L1正则
                    }
                    changas = changsWeight(wClone, w);
                    caculate++;
                    System.out.println("迭代次数是:" + caculate + "  权重是:"
                            + Arrays.toString(w));
                }

                break;
            case 2://随机梯度下降
                while (changas > 0.0001) {
                    float[] wClone = w.clone();
                    for (int s = 0; s < size; s++) {
                        float lire = innerProduct(w, datas.get(s));
                        float out = sigmoid(lire);
                        float error = labels.get(s) - out;
                        for (int d = 0; d < dim; d++) {
                            w[d] += step * error * datas.get(s).get(d);
                        }
                    }
                    changas = changsWeight(wClone, w);
                    caculate++;

                    System.out.println("迭代次数是:" + caculate + "  权重是:"
                            + Arrays.toString(w));
                }
                break;
            default:
                break;
        }

    }

    private float changsWeight(float[] wClone, float[] w) {
        float changs = 0;
        for (int i = 0; i < w.length; i++) {
            changs += Math.pow(w[i] - wClone[i], 2);
        }

        return (float) Math.sqrt(changs);

    }

    private float innerProduct(float[] w, List<Float> x) {
        float sum = 0;
        for (int i = 0; i < w.length; i++) {
            sum += w[i] * x.get(i);
        }

        return sum;
    }

    private float sigmoid(float src) {
        return (float) (1.0 / (1 + Math.exp(-src)));
    }

}