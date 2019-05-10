package com.spring.store.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;

import com.spring.store.utils.ErrorModel;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
public class BaseTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    @Qualifier("transactionManager")
    protected PlatformTransactionManager txManager;

    protected void runInTransaction(TransactionCallback<Void> callback) {
        new TransactionTemplate(txManager).execute(callback);
    }

    protected <T> T postForObject(String url, Object data, Class<T> cls) throws Exception {
        return postForObject(url, data, new LinkedMultiValueMap<>(), cls);
    }

    protected <T> T postForObject(String url, Object data, MultiValueMap<String, String> params, Class<T> cls) throws Exception {
        String content = this.mockMvc.perform(
                post(url).params(params).content(objectMapper.writeValueAsBytes(data)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(content, cls);
    }

    protected <T> T putForObject(String url, Object data, Class<T> cls) throws Exception {
        return putForObject(url, data, new LinkedMultiValueMap<>(), cls);
    }

    protected <T> T putForObject(String url, Object data, MultiValueMap<String, String> params, Class<T> cls) throws Exception {
        String content = this.mockMvc.perform(
                put(url).params(params).content(objectMapper.writeValueAsBytes(data)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(content, cls);
    }

    protected <T> T getForObject(String url, Class<T> cls) throws Exception {
        return getForObject(url, new LinkedMultiValueMap<>(), cls);
    }

    protected <T> T getForObject(String url, MultiValueMap<String, String> params, Class<T> cls) throws Exception {
        String content = this.mockMvc.perform(get(url).params(params))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readValue(content, cls);
    }

    /**
     * execute get call and expect success and List as a result
     *
     * @param url url to be called
     * @param <T> Class type of returned List
     * @return List ot T objects
     */
    protected <T> List<T> getForList(String url, Class<T> cls) throws Exception {
        return getForList(url, new LinkedMultiValueMap<>(), cls);
    }

    /**
     * execute get call and expect success and List as a result
     *
     * @param url    url to be called
     * @param params request params
     * @param <T>    Class type of returned List
     * @return List ot T objects
     */
    protected <T> List<T> getForList(String url, MultiValueMap<String, String> params, Class<T> cls) throws Exception {
        String content = this.mockMvc.perform(get(url).params(params))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(content, objectMapper.getTypeFactory().constructCollectionType(List.class, cls));
    }

    /**
     * execute get call and expect success and Set as a result
     *
     * @param url url to be called
     * @param <T> Class type of returned Set
     * @return Set ot T objects
     */
    protected <T> Set<T> getForSet(String url, Class<T> cls) throws Exception {
        return getForSet(url, new LinkedMultiValueMap<>(), cls);
    }

    /**
     * execute get call and expect success and Set as a result
     *
     * @param url    url to be called
     * @param params request params
     * @param <T>    Class type of returned Set
     * @return Set ot T objects
     */
    protected <T> Set<T> getForSet(String url, MultiValueMap<String, String> params, Class<T> cls) throws Exception {
        String content = this.mockMvc.perform(get(url).params(params))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(content, objectMapper.getTypeFactory().constructCollectionType(Set.class, cls));
    }

    /**
     * execute get call and expect success and Set as a result
     *
     * @param url url to be called
     * @param <T> Class type of returned Set
     * @return Set ot T objects
     */
    protected <T> Page<T> getForPage(String url, TypeReference<?> type) throws Exception {
        return getForPage(url, new LinkedMultiValueMap<>(), type);
    }

    /**
     * execute get call and expect success and Set as a result
     *
     * @param url    url to be called
     * @param params request params
     * @param <T>    Class type of returned Set
     * @return Set ot T objects
     */
    protected <T> Page<T> getForPage(String url, MultiValueMap<String, String> params, TypeReference<?> type) throws Exception {
        assertNotNull(params, "Params cannot be null");
        String content = this.mockMvc.perform(get(url).params(params))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(content, type);
    }


    /**
     * execute post call and expect an error response
     *
     * @param url            url to be called
     * @param data           payload object
     * @param expectedStatus status to be expected. e.g. MockMvcResultMatchers.status().isBadRequest()
     * @return ErrorModel
     */
    protected ErrorModel postForError(String url, Object data, ResultMatcher expectedStatus) throws Exception {
        String content = this.mockMvc.perform(
                post(url).content(objectMapper.writeValueAsBytes(data)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(expectedStatus)
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(content, ErrorModel.class);
    }

    /**
     * execute put call and expect an error response
     *
     * @param url            url to be called
     * @param data           payload object
     * @param expectedStatus status to be expected. e.g. MockMvcResultMatchers.status().isBadRequest()
     * @return ErrorModel
     */
    protected ErrorModel putForError(String url, Object data, ResultMatcher expectedStatus) throws Exception {
        String content = this.mockMvc.perform(
                put(url).content(objectMapper.writeValueAsBytes(data)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(expectedStatus)
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(content, ErrorModel.class);
    }

    /**
     * execute get call and expect an error response
     *
     * @param url            url to be called
     * @param expectedStatus status to be expected. e.g. MockMvcResultMatchers.status().isNotFound()
     * @return ErrorModel
     */
    protected ErrorModel getForError(String url, ResultMatcher expectedStatus) throws Exception {
        String content = this.mockMvc.perform(get(url)).andExpect(expectedStatus).andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(content, ErrorModel.class);
    }

    protected  <T> MockHttpServletResponse deleteForObject(String path, String id) throws Exception {
        return this.mockMvc.perform(delete(path + "/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    }

}
