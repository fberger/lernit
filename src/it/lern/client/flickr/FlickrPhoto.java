package it.lern.client.flickr;

import it.lern.client.flickr.FlickrPhotoSizes.Size;

public class FlickrPhoto {

	private final FlickrPhotoResult photo;
	private final FlickrPhotoSizes sizes;

	public FlickrPhoto(FlickrPhotoResult photo, FlickrPhotoSizes sizes) {
		this.photo = photo;
		this.sizes = sizes;
	}
	
	public String getSourceUrl(Size size) {
		return sizes.getSourceUrls(size);
	}
	
	public String getId() {
		return photo.getId();
	}
}
