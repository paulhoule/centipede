package ${package};

import java.util.List;
import com.google.common.collect.Lists;



public class Main extends CentipedeShell {

    public List<String> getApplicationContextPath() {
        String resourceDir="${package}";
        return Lists.newArrayList(resourceDir+"/applicationContext.xml");
    }

    @Override
    public String getShellName() {
        return "${artifactId}";
    }

    public static void main(String[] args) {
        new Main().run(args);
    }
}
