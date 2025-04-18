package api.stock.manager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;


@RestController
class HealthController {
    OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();


    @GetMapping("/health")
    public String checkHealth() {
        return "OK";
    }

    @GetMapping("/usage")
    public String getUsageMetrics(){
        double systemCpuLoad = osBean.getSystemCpuLoad() * 100;

        long totalPhysicalMemory = osBean.getTotalPhysicalMemorySize();
        long usedPhysicalMemory = totalPhysicalMemory - osBean.getFreePhysicalMemorySize();
        double memoryUsagePercentage = ((double) usedPhysicalMemory / totalPhysicalMemory) * 100;

        return String.format("System CPU Usage: %.2f%%, Used RAM: (%.2f%%)",
                systemCpuLoad, memoryUsagePercentage);
    }

}
