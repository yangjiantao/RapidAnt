package io.jiantao.example.wanandroid.api;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.google.common.truth.Truth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.jiantao.example.wanandroid.db.entity.WanAndroidArticle;
import io.jiantao.example.wanandroid.util.LiveDataCallAdapterFactory;
import io.jiantao.example.wanandroid.util.LiveDataTestUtil;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019-05-15
 */
@RunWith(JUnit4.class)
public class WanAndroidServiceTest {
    /**
     * mock android sdk class
     */
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private WanAndroidService mService;
    private MockWebServer mockWebServer;

    @Before
    public void createService() {
        // Create a MockWebServer. These are lean enough that you can create a new
        // instance for every unit test.
        mockWebServer = new MockWebServer();
        mService = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(WanAndroidService.class);

    }

    @Test
    public void getArticles() throws InterruptedException, IOException {

        enqueueResponse("selected_articles.json");
        BaseDataList<WanAndroidArticle> body = LiveDataTestUtil.getValue(mService.getSelectedArticles(0)).getBody();

        // Optional: confirm that your app made the HTTP requests you were expecting.
        RecordedRequest request = mockWebServer.takeRequest();
        Truth.assertThat(request.getPath()).isEqualTo("/article/list/0/json");

        Truth.assertThat(body).isNotNull();
        List<WanAndroidArticle> articles = body.getData();
        Truth.assertThat(articles).isNotNull();

        Truth.assertThat(articles.size()).isEqualTo(20);

        Truth.assertThat(articles.get(5).author).isEqualTo("林帅并不帅");


    }

    private void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.emptyMap());
    }

    /**
     * 设置本地json数据到mockWebServer
     *
     * @param fileName
     * @param headers
     * @throws IOException
     */
    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("api-response/" + fileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            mockResponse.addHeader(header.getKey(), header.getValue());
        }
        mockWebServer.enqueue(mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8)));
    }

    @After
    public void stopService() throws IOException {
        mockWebServer.shutdown();
    }
}
