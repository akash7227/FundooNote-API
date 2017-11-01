package com.akash.service;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akash.model.Note;

@Service("elasticSearchService")
public class ElasticSearchService {
	@Autowired
	NoteService noteService;
	
	private TransportClient client;

	public void init() throws UnknownHostException {
		client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

	}

	//@Scheduled(fixedDelay = 100000 * 60)
	public void insertDataIntoElasticServer() throws IOException {

		List<Note> allnote = noteService.selectAllNote();

		for (Note note : allnote) {
			String id = String.valueOf(note.getId());
			XContentBuilder notedata = jsonBuilder()
					.startObject()
					.field("user_id", note.getUser().getUser_id())
					.field("title", note.getTitle())
					.field("description", note.getDescription()).endObject();
			
			String data = notedata.string();
			IndexResponse response = client
									.prepareIndex("fundoonote", "note")
									.setId(id)
									.setSource(data)
									.execute()
									.actionGet();
		}

	}

	public boolean search(Note note) throws UnknownHostException {
		
		QueryBuilder queryBuilder = QueryBuilders
				.boolQuery()
				.must(QueryBuilders.matchQuery("user_id", note.getUser().getUser_id()))
				.must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("title", note.getTitle()))
				.should(QueryBuilders.matchQuery("description", note.getDescription())));

		SearchResponse response = client
				.prepareSearch("fundoonote")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(queryBuilder)
				.execute()
				.actionGet();

		SearchHit[] searchhit = response.getHits().getHits();
		for (SearchHit data : searchhit) {
			System.out.println("data is " + data.getSourceAsString());
		}
		if (searchhit != null) {
			return true;
		}
		return false;

	}

	
	public boolean deleteDataFromElastic(Note note) throws IOException {
		
		String id = String.valueOf(note.getId());
		DeleteResponse deleteResponse = client
				.prepareDelete("fundoonote", "note", id)
				.execute()
				.actionGet();
		System.out.println("deleteDataFromElastic" + deleteResponse.toString());

		if (deleteResponse != null) {
			return true;
		}
		return false;
	}
}
