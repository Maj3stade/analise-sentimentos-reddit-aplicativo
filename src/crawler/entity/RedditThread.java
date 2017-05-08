
package crawler.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "contest_mode", "banned_by", "media_embed", "subreddit", "selftext_html", "selftext", "likes",
	"suggested_sort", "user_reports", "secure_media", "link_flair_text", "id", "view_count", "secure_media_embed",
	"clicked", "score", "report_reasons", "author", "saved", "mod_reports", "name", "subreddit_name_prefixed",
	"approved_by", "over_18", "domain", "hidden", "thumbnail", "subreddit_id", "edited", "link_flair_css_class",
	"author_flair_css_class", "gilded", "downs", "brand_safe", "archived", "removal_reason", "can_gild", "is_self",
	"hide_score", "spoiler", "permalink", "num_reports", "locked", "stickied", "created", "url",
	"author_flair_text", "quarantine", "title", "created_utc", "distinguished", "media", "num_comments", "visited",
	"subreddit_type", "ups" })
public class RedditThread {

	@JsonProperty("contest_mode")
	private Boolean contestMode;
	@JsonProperty("banned_by")
	private Object bannedBy;
	@JsonProperty("media_embed")
	private MediaEmbed mediaEmbed;
	@JsonProperty("subreddit")
	private String subreddit;
	@JsonProperty("selftext_html")
	private Object selftextHtml;
	@JsonProperty("selftext")
	private String selftext;
	@JsonProperty("likes")
	private Object likes;
	@JsonProperty("suggested_sort")
	private Object suggestedSort;
	@JsonProperty("user_reports")
	private List<Object> userReports = null;
	@JsonProperty("secure_media")
	private Object secureMedia;
	@JsonProperty("link_flair_text")
	private Object linkFlairText;
	
	@Id
	@JsonProperty("id")
	private String id;
	@JsonProperty("view_count")
	private Object viewCount;
	@JsonProperty("secure_media_embed")
	private SecureMediaEmbed secureMediaEmbed;
	@JsonProperty("clicked")
	private Boolean clicked;
	@JsonProperty("score")
	private Integer score;
	@JsonProperty("report_reasons")
	private Object reportReasons;
	@JsonProperty("author")
	private String author;
	@JsonProperty("saved")
	private Boolean saved;
	@JsonProperty("mod_reports")
	private List<Object> modReports = null;
	@JsonProperty("name")
	private String name;
	@JsonProperty("subreddit_name_prefixed")
	private String subredditNamePrefixed;
	@JsonProperty("approved_by")
	private Object approvedBy;
	@JsonProperty("over_18")
	private Boolean over18;
	@JsonProperty("domain")
	private String domain;
	@JsonProperty("hidden")
	private Boolean hidden;
	@JsonProperty("thumbnail")
	private String thumbnail;
	@JsonProperty("subreddit_id")
	private String subredditId;
	@JsonProperty("edited")
	private Object edited;
	@JsonProperty("link_flair_css_class")
	private Object linkFlairCssClass;
	@JsonProperty("author_flair_css_class")
	private Object authorFlairCssClass;
	@JsonProperty("gilded")
	private Integer gilded;
	@JsonProperty("downs")
	private Integer downs;
	@JsonProperty("brand_safe")
	private Boolean brandSafe;
	@JsonProperty("archived")
	private Boolean archived;
	@JsonProperty("removal_reason")
	private Object removalReason;
	@JsonProperty("can_gild")
	private Boolean canGild;
	@JsonProperty("is_self")
	private Boolean isSelf;
	@JsonProperty("hide_score")
	private Boolean hideScore;
	@JsonProperty("spoiler")
	private Boolean spoiler;
	@JsonProperty("permalink")
	private String permalink;
	@JsonProperty("num_reports")
	private Object numReports;
	@JsonProperty("locked")
	private Boolean locked;
	@JsonProperty("stickied")
	private Boolean stickied;
	@JsonProperty("created")
	private Double created;
	@JsonProperty("url")
	private String url;
	@JsonProperty("author_flair_text")
	private Object authorFlairText;
	@JsonProperty("quarantine")
	private Boolean quarantine;
	@JsonProperty("title")
	private String title;
	@JsonProperty("created_utc")
	private Double createdUtc;
	@JsonProperty("distinguished")
	private Object distinguished;
	@JsonProperty("media")
	private Object media;
	@JsonProperty("num_comments")
	private Integer numComments;
	@JsonProperty("visited")
	private Boolean visited;
	@JsonProperty("subreddit_type")
	private String subredditType;
	@JsonProperty("ups")
	private Integer ups;
	
	@JsonProperty("contest_mode")
	public Boolean getContestMode() {
		return contestMode;
	}

	@JsonProperty("contest_mode")
	public void setContestMode(Boolean contestMode) {
		this.contestMode = contestMode;
	}

	@JsonProperty("banned_by")
	public Object getBannedBy() {
		return bannedBy;
	}

	@JsonProperty("banned_by")
	public void setBannedBy(Object bannedBy) {
		this.bannedBy = bannedBy;
	}

	@JsonProperty("media_embed")
	public MediaEmbed getMediaEmbed() {
		return mediaEmbed;
	}

	@JsonProperty("media_embed")
	public void setMediaEmbed(MediaEmbed mediaEmbed) {
		this.mediaEmbed = mediaEmbed;
	}

	@JsonProperty("subreddit")
	public String getSubreddit() {
		return subreddit;
	}

	@JsonProperty("subreddit")
	public void setSubreddit(String subreddit) {
		this.subreddit = subreddit;
	}

	@JsonProperty("selftext_html")
	public Object getSelftextHtml() {
		return selftextHtml;
	}

	@JsonProperty("selftext_html")
	public void setSelftextHtml(Object selftextHtml) {
		this.selftextHtml = selftextHtml;
	}

	@JsonProperty("selftext")
	public String getSelftext() {
		return selftext;
	}

	@JsonProperty("selftext")
	public void setSelftext(String selftext) {
		this.selftext = selftext;
	}

	@JsonProperty("likes")
	public Object getLikes() {
		return likes;
	}

	@JsonProperty("likes")
	public void setLikes(Object likes) {
		this.likes = likes;
	}

	@JsonProperty("suggested_sort")
	public Object getSuggestedSort() {
		return suggestedSort;
	}

	@JsonProperty("suggested_sort")
	public void setSuggestedSort(Object suggestedSort) {
		this.suggestedSort = suggestedSort;
	}

	@JsonProperty("user_reports")
	public List<Object> getUserReports() {
		return userReports;
	}

	@JsonProperty("user_reports")
	public void setUserReports(List<Object> userReports) {
		this.userReports = userReports;
	}

	@JsonProperty("secure_media")
	public Object getSecureMedia() {
		return secureMedia;
	}

	@JsonProperty("secure_media")
	public void setSecureMedia(Object secureMedia) {
		this.secureMedia = secureMedia;
	}

	@JsonProperty("link_flair_text")
	public Object getLinkFlairText() {
		return linkFlairText;
	}

	@JsonProperty("link_flair_text")
	public void setLinkFlairText(Object linkFlairText) {
		this.linkFlairText = linkFlairText;
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("view_count")
	public Object getViewCount() {
		return viewCount;
	}

	@JsonProperty("view_count")
	public void setViewCount(Object viewCount) {
		this.viewCount = viewCount;
	}

	@JsonProperty("secure_media_embed")
	public SecureMediaEmbed getSecureMediaEmbed() {
		return secureMediaEmbed;
	}

	@JsonProperty("secure_media_embed")
	public void setSecureMediaEmbed(SecureMediaEmbed secureMediaEmbed) {
		this.secureMediaEmbed = secureMediaEmbed;
	}

	@JsonProperty("clicked")
	public Boolean getClicked() {
		return clicked;
	}

	@JsonProperty("clicked")
	public void setClicked(Boolean clicked) {
		this.clicked = clicked;
	}

	@JsonProperty("score")
	public Integer getScore() {
		return score;
	}

	@JsonProperty("score")
	public void setScore(Integer score) {
		this.score = score;
	}

	@JsonProperty("report_reasons")
	public Object getReportReasons() {
		return reportReasons;
	}

	@JsonProperty("report_reasons")
	public void setReportReasons(Object reportReasons) {
		this.reportReasons = reportReasons;
	}

	@JsonProperty("author")
	public String getAuthor() {
		return author;
	}

	@JsonProperty("author")
	public void setAuthor(String author) {
		this.author = author;
	}

	@JsonProperty("saved")
	public Boolean getSaved() {
		return saved;
	}

	@JsonProperty("saved")
	public void setSaved(Boolean saved) {
		this.saved = saved;
	}

	@JsonProperty("mod_reports")
	public List<Object> getModReports() {
		return modReports;
	}

	@JsonProperty("mod_reports")
	public void setModReports(List<Object> modReports) {
		this.modReports = modReports;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("subreddit_name_prefixed")
	public String getSubredditNamePrefixed() {
		return subredditNamePrefixed;
	}

	@JsonProperty("subreddit_name_prefixed")
	public void setSubredditNamePrefixed(String subredditNamePrefixed) {
		this.subredditNamePrefixed = subredditNamePrefixed;
	}

	@JsonProperty("approved_by")
	public Object getApprovedBy() {
		return approvedBy;
	}

	@JsonProperty("approved_by")
	public void setApprovedBy(Object approvedBy) {
		this.approvedBy = approvedBy;
	}

	@JsonProperty("over_18")
	public Boolean getOver18() {
		return over18;
	}

	@JsonProperty("over_18")
	public void setOver18(Boolean over18) {
		this.over18 = over18;
	}

	@JsonProperty("domain")
	public String getDomain() {
		return domain;
	}

	@JsonProperty("domain")
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@JsonProperty("hidden")
	public Boolean getHidden() {
		return hidden;
	}

	@JsonProperty("hidden")
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	@JsonProperty("thumbnail")
	public String getThumbnail() {
		return thumbnail;
	}

	@JsonProperty("thumbnail")
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@JsonProperty("subreddit_id")
	public String getSubredditId() {
		return subredditId;
	}

	@JsonProperty("subreddit_id")
	public void setSubredditId(String subredditId) {
		this.subredditId = subredditId;
	}

	@JsonProperty("edited")
	public Object getEdited() {
		return edited;
	}

	@JsonProperty("edited")
	public void setEdited(Object edited) {
		this.edited = edited;
	}

	@JsonProperty("link_flair_css_class")
	public Object getLinkFlairCssClass() {
		return linkFlairCssClass;
	}

	@JsonProperty("link_flair_css_class")
	public void setLinkFlairCssClass(Object linkFlairCssClass) {
		this.linkFlairCssClass = linkFlairCssClass;
	}

	@JsonProperty("author_flair_css_class")
	public Object getAuthorFlairCssClass() {
		return authorFlairCssClass;
	}

	@JsonProperty("author_flair_css_class")
	public void setAuthorFlairCssClass(Object authorFlairCssClass) {
		this.authorFlairCssClass = authorFlairCssClass;
	}

	@JsonProperty("gilded")
	public Integer getGilded() {
		return gilded;
	}

	@JsonProperty("gilded")
	public void setGilded(Integer gilded) {
		this.gilded = gilded;
	}

	@JsonProperty("downs")
	public Integer getDowns() {
		return downs;
	}

	@JsonProperty("downs")
	public void setDowns(Integer downs) {
		this.downs = downs;
	}

	@JsonProperty("brand_safe")
	public Boolean getBrandSafe() {
		return brandSafe;
	}

	@JsonProperty("brand_safe")
	public void setBrandSafe(Boolean brandSafe) {
		this.brandSafe = brandSafe;
	}

	@JsonProperty("archived")
	public Boolean getArchived() {
		return archived;
	}

	@JsonProperty("archived")
	public void setArchived(Boolean archived) {
		this.archived = archived;
	}

	@JsonProperty("removal_reason")
	public Object getRemovalReason() {
		return removalReason;
	}

	@JsonProperty("removal_reason")
	public void setRemovalReason(Object removalReason) {
		this.removalReason = removalReason;
	}

	@JsonProperty("can_gild")
	public Boolean getCanGild() {
		return canGild;
	}

	@JsonProperty("can_gild")
	public void setCanGild(Boolean canGild) {
		this.canGild = canGild;
	}

	@JsonProperty("is_self")
	public Boolean getIsSelf() {
		return isSelf;
	}

	@JsonProperty("is_self")
	public void setIsSelf(Boolean isSelf) {
		this.isSelf = isSelf;
	}

	@JsonProperty("hide_score")
	public Boolean getHideScore() {
		return hideScore;
	}

	@JsonProperty("hide_score")
	public void setHideScore(Boolean hideScore) {
		this.hideScore = hideScore;
	}

	@JsonProperty("spoiler")
	public Boolean getSpoiler() {
		return spoiler;
	}

	@JsonProperty("spoiler")
	public void setSpoiler(Boolean spoiler) {
		this.spoiler = spoiler;
	}

	@JsonProperty("permalink")
	public String getPermalink() {
		return permalink;
	}

	@JsonProperty("permalink")
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	@JsonProperty("num_reports")
	public Object getNumReports() {
		return numReports;
	}

	@JsonProperty("num_reports")
	public void setNumReports(Object numReports) {
		this.numReports = numReports;
	}

	@JsonProperty("locked")
	public Boolean getLocked() {
		return locked;
	}

	@JsonProperty("locked")
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	@JsonProperty("stickied")
	public Boolean getStickied() {
		return stickied;
	}

	@JsonProperty("stickied")
	public void setStickied(Boolean stickied) {
		this.stickied = stickied;
	}

	@JsonProperty("created")
	public Double getCreated() {
		return created;
	}

	@JsonProperty("created")
	public void setCreated(Double created) {
		this.created = created;
	}

	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	@JsonProperty("url")
	public void setUrl(String url) {
		this.url = url;
	}

	@JsonProperty("author_flair_text")
	public Object getAuthorFlairText() {
		return authorFlairText;
	}

	@JsonProperty("author_flair_text")
	public void setAuthorFlairText(Object authorFlairText) {
		this.authorFlairText = authorFlairText;
	}

	@JsonProperty("quarantine")
	public Boolean getQuarantine() {
		return quarantine;
	}

	@JsonProperty("quarantine")
	public void setQuarantine(Boolean quarantine) {
		this.quarantine = quarantine;
	}

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("created_utc")
	public Double getCreatedUtc() {
		return createdUtc;
	}

	@JsonProperty("created_utc")
	public void setCreatedUtc(Double createdUtc) {
		this.createdUtc = createdUtc;
	}

	@JsonProperty("distinguished")
	public Object getDistinguished() {
		return distinguished;
	}

	@JsonProperty("distinguished")
	public void setDistinguished(Object distinguished) {
		this.distinguished = distinguished;
	}

	@JsonProperty("media")
	public Object getMedia() {
		return media;
	}

	@JsonProperty("media")
	public void setMedia(Object media) {
		this.media = media;
	}

	@JsonProperty("num_comments")
	public Integer getNumComments() {
		return numComments;
	}

	@JsonProperty("num_comments")
	public void setNumComments(Integer numComments) {
		this.numComments = numComments;
	}

	@JsonProperty("visited")
	public Boolean getVisited() {
		return visited;
	}

	@JsonProperty("visited")
	public void setVisited(Boolean visited) {
		this.visited = visited;
	}

	@JsonProperty("subreddit_type")
	public String getSubredditType() {
		return subredditType;
	}

	@JsonProperty("subreddit_type")
	public void setSubredditType(String subredditType) {
		this.subredditType = subredditType;
	}

	@JsonProperty("ups")
	public Integer getUps() {
		return ups;
	}

	@JsonProperty("ups")
	public void setUps(Integer ups) {
		this.ups = ups;
	}

	
}
