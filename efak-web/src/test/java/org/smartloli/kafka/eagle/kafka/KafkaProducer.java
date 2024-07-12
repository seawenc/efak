package org.smartloli.kafka.eagle.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import java.util.Properties;
import java.util.concurrent.Future;
/**
 * kafka消息生产者测试
 */
public class KafkaProducer {

    public static void main(String[] args)throws Exception {
        String topic="kafka-topic-unstruct";
        Properties props= KafkaHelper.getKafkaConf();
        org.apache.kafka.clients.producer.KafkaProducer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
        String msg="{\"data\":\"{\\\"object_kind\\\":\\\"push\\\",\\\"event_name\\\":\\\"push\\\",\\\"before\\\":\\\"6f01562f6751e75cc52649c0c3e06845ca0de18e\\\",\\\"after\\\":\\\"793dfaa9797fb55a5b13031d7c357a49c1abb0a5\\\",\\\"ref\\\":\\\"refs/heads/wsj2-16\\\",\\\"checkout_sha\\\":\\\"793dfaa9797fb55a5b13031d7c357a49c1abb0a5\\\",\\\"message\\\":null,\\\"user_id\\\":901,\\\"user_name\\\":\\\"吴绍军\\\",\\\"user_username\\\":\\\"73282056\\\",\\\"user_email\\\":\\\"\\\",\\\"user_avatar\\\":\\\"https://secure.gravatar.com/avatar/cca0d09e5fd7b68e3ec28f39d174f8f8?s=80\\\\u0026d=identicon\\\",\\\"project_id\\\":394,\\\"project\\\":{\\\"id\\\":394,\\\"name\\\":\\\"bsppay-bsppay\\\",\\\"description\\\":\\\"\\\",\\\"web_url\\\":\\\"https://gitlab.gtp.x/gds-bsppay/bsppay\\\",\\\"avatar_url\\\":null,\\\"git_ssh_url\\\":\\\"ssh://git@gitlab.gtp.x:30022/gds-bsppay/bsppay.git\\\",\\\"git_http_url\\\":\\\"https://gitlab.gtp.x/gds-bsppay/bsppay.git\\\",\\\"namespace\\\":\\\"渠道合约区-BSP支付平台\\\",\\\"visibility_level\\\":0,\\\"path_with_namespace\\\":\\\"gds-bsppay/bsppay\\\",\\\"default_branch\\\":\\\"master\\\",\\\"ci_config_path\\\":null,\\\"homepage\\\":\\\"https://gitlab.gtp.x/gds-bsppay/bsppay\\\",\\\"url\\\":\\\"ssh://git@gitlab.gtp.x:30022/gds-bsppay/bsppay.git\\\",\\\"ssh_url\\\":\\\"ssh://git@gitlab.gtp.x:30022/gds-bsppay/bsppay.git\\\",\\\"http_url\\\":\\\"https://gitlab.gtp.x/gds-bsppay/bsppay.git\\\"},\\\"commits\\\":[{\\\"id\\\":\\\"793dfaa9797fb55a5b13031d7c357a49c1abb0a5\\\",\\\"message\\\":\\\"uatpinjector单元测试\\\\n\\\",\\\"timestamp\\\":\\\"2023-04-12T07:31:03Z\\\",\\\"url\\\":\\\"https://gitlab.gtp.x/gds-bsppay/bsppay/commit/793dfaa9797fb55a5b13031d7c357a49c1abb0a5\\\",\\\"author\\\":{\\\"name\\\":\\\"wushaojun\\\",\\\"email\\\":\\\"wushaojun@travelsky.com.cn\\\"},\\\"added\\\":[],\\\"modified\\\":[\\\"bsppay/test/uatp/CMakeLists.txt\\\",\\\"bsppay/test/uatp/uatpinjector_test.cpp\\\"],\\\"removed\\\":[]}],\\\"total_commits_count\\\":1,\\\"push_options\\\":{},\\\"repository\\\":{\\\"name\\\":\\\"bsppay-bsppay\\\",\\\"url\\\":\\\"ssh://git@gitlab.gtp.x:30022/gds-bsppay/bsppay.git\\\",\\\"description\\\":\\\"\\\",\\\"homepage\\\":\\\"https://gitlab.gtp.x/gds-bsppay/bsppay\\\",\\\"git_http_url\\\":\\\"https://gitlab.gtp.x/gds-bsppay/bsppay.git\\\",\\\"git_ssh_url\\\":\\\"ssh://git@gitlab.gtp.x:30022/gds-bsppay/bsppay.git\\\",\\\"visibility_level\\\":0}}\",\"id\":\"GITLABGTPX\"}";
        Future<RecordMetadata> future= producer.send(new ProducerRecord<>(topic,msg));
        Thread.sleep(8000);
//        for(int i=0;i<20000;i++){
//            Thread.sleep(500);
//            Future<RecordMetadata> future= producer.send(new ProducerRecord<>(topic,"发送消息"+i));
//            System.out.println(i+"->topic:" + topic+",partition = "+ future.get().partition());
//        }
    }

    private static String createLongSizek(int sizek){
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<sizek;i++){
            sb.append("aaabbcc");
        }
        return sb.toString();
    }
}