package it.lern.client.flickr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FlickrPhotoSearch {
	
	private final FlickrApiRequestBuilder builder = new FlickrApiRequestBuilder();
	
	public void search(String searchTerm, final int maxNum, final AsyncCallback<List<FlickrPhoto>> callback) {
		builder.request("flickr.photos.search", Collections.singletonMap("text", searchTerm), new AsyncCallback<FlickrPhotos>() {
			@Override
			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}
			@Override
			public void onSuccess(FlickrPhotos result) {
				JsArray<FlickrPhotoResult> photos = result.getPhotos();
				final List<FlickrPhoto> results = new ArrayList<FlickrPhoto>();
				final int[] failures = new int[] { 0 };
				final int min = Math.min(maxNum, photos.length());
				for (int i = 0; i < min; i++) {
					final FlickrPhotoResult photo = photos.get(i);
					System.out.println("requesting: " + photo.getId());
					builder.request("flickr.photos.getSizes", Collections.singletonMap("photo_id", photo.getId()), new AsyncCallback<FlickrPhotoSizes>() {
						@Override
						public void onFailure(Throwable caught) {
							int count = ++failures[0];
							if (count + results.size() == min) {
								if (results.size() > 0) {
									callback.onSuccess(results);
								} else {
									callback.onFailure(caught);
								}
							}
						}
						@Override
						public void onSuccess(FlickrPhotoSizes sizes) {
							System.out.println("success  " + photo.getId());
							results.add(new FlickrPhoto(photo, sizes));
							System.out.println(results.size());
							if (failures[0] + results.size() == min) {
								callback.onSuccess(results);
							}
						}
					});
				}
			}
		});
	}
	
}
