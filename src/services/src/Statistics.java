import java.util.Arrays;

public class Statistics 
{
    float[] data;
    int size;   

    public Statistics(float[] data) 
    {
        this.data = data;
        size = data.length;
    }   

    float getMean()
    {
        float sum = 0.0f;
        for(float a : data)
            sum += a;
        return sum/size;
    }

    float getVariance()
    {
        float mean = getMean();
        float temp = 0f;
        for(float a :data)
            temp += (mean-a)*(mean-a);
        return temp/size;
    }

    float getStdDev()
    {
        return (float) Math.sqrt(getVariance());
    }

    public float median() 
    {
       Arrays.sort(data);

       if (data.length % 2 == 0) 
       {
          return (float) ((data[(data.length / 2) - 1] + data[data.length / 2]) / 2.0);
       } 
       else 
       {
          return data[data.length / 2];
       }
    }
}
