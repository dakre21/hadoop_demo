import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobStatus;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {

  public static class TokenizerMapper
      extends Mapper<Object, Text, Text, IntWritable> {

      private final static IntWritable one = new IntWritable(1);
      private Text word = new Text();

      public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
          String cleanLine = value.toString().toLowerCase().replaceAll("[_|$#<>\\^=\\[\\]\\*/\\\\,;,.\\-:()?!\"']", " ");
          StringTokenizer itr = new StringTokenizer(cleanLine);
          while (itr.hasMoreTokens()) {
	      try {
		  String element = itr.nextToken();
                  if (element.contains("website")) {
                      word.set(element);
                      context.write(word, one);
	          }
	      } catch (NoSuchElementException e) {
	      }
          }
      }
  }

  public static class IntSumReducer
      extends Reducer<Text, IntWritable, Text, IntWritable> {
      private IntWritable result = new IntWritable();

      public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
          int sum = 0;
          for (IntWritable val : values) {
              sum += val.get();
          }
          result.set(sum);
          context.write(key, result);
      }
  }


    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
	WordCount count = new WordCount();
	Path pt = new Path(".");
	FileSystem fs = pt.getFileSystem(conf);

        Job job = Job.getInstance(conf, "word count");

        job.setJar("WordCount.jar");
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

	job.waitForCompletion(true);
	
	// Begin merging results into one file
	Path dstPath = new Path(args[1] + "/results.txt");
	Path inPath = new Path(args[1]);

	FileUtil.copyMerge(fs, inPath, fs, dstPath, false, conf, null);
	BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(dstPath)));

	String line = "";
	String freq_str = "";
	String web = "";
	String web_1 = "";
	String web_2 = "";
	String web_3 = "";
	String web_4 = "";
	String web_5 = "";
	int max_1 = 0;
	int max_2 = 0;
	int max_3 = 0;
	int max_4 = 0;
	int max_5 = 0;	
	int temp = 0;
	while ((line = br.readLine()) != null) {
	    freq_str = line.substring(line.length()-2, line.length());
	    temp = Integer.parseInt(freq_str);
	    web = line.substring(0, line.length()-2);

	    if (temp > max_1) {
	        max_5 = max_4;
		web_5 = web_4;
		max_4 = max_3;
		web_4 = web_3;
		max_3 = max_2;
		web_3 = web_2;
		max_2 = max_1;
		web_2 = web_1;
		max_1 = temp;
		web_1 = web;
	    } else if (temp > max_2) {
	        max_5 = max_4;
		web_5 = web_4;
		max_4 = max_3;
		web_4 = web_3;
		max_3 = max_2;
		web_3 = web_2;
		max_2 = temp;
		web_2 = web;
 	    } else if (temp > max_3) {
	        max_5 = max_4;
		web_5 = web_4;
		max_4 = max_3;
		web_4 = web_3;
		max_3 = temp;
		web_3 = web;
            } else if (temp > max_4) {
	        max_5 = max_4;
		web_5 = web_4;
		max_4 = temp;
		web_4 = web;
            } else if (temp > max_5) {
	        max_5 = temp;
		web_5 = web;
            }
	}

	System.out.println("Most frequently visited web sites (top 5)");
	System.out.println("1: " + web_1 + " was accessed " + String.valueOf(max_1) + " times");
	System.out.println("2: " + web_2 + " was accessed " + String.valueOf(max_2) + " times");
	System.out.println("3: " + web_3 + " was accessed " + String.valueOf(max_3) + " times");
	System.out.println("4: " + web_4 + " was accessed " + String.valueOf(max_4) + " times");
	System.out.println("5: " + web_5 + " was accessed " + String.valueOf(max_5) + " times");
    }
}


