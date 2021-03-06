
package crawler.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "contest_mode",
    "banned_by",
    "media_embed",
    "subreddit",
    "selftext_html",
    "selftext",
    "likes",
    "suggested_sort",
    "user_reports",
    "secure_media",
    "saved",
    "id",
    "view_count",
    "secure_media_embed",
    "clicked",
    "score",
    "report_reasons",
    "author",
    "link_flair_text",
    "subreddit_name_prefixed",
    "approved_by",
    "over_18",
    "domain",
    "hidden",
    "preview",
    "num_comments",
    "thumbnail",
    "subreddit_id",
    "edited",
    "link_flair_css_class",
    "author_flair_css_class",
    "gilded",
    "downs",
    "brand_safe",
    "archived",
    "removal_reason",
    "post_hint",
    "stickied",
    "can_gild",
    "is_self",
    "hide_score",
    "spoiler",
    "permalink",
    "subreddit_type",
    "locked",
    "name",
    "created",
    "url",
    "author_flair_text",
    "quarantine",
    "title",
    "created_utc",
    "ups",
    "media",
    "upvote_ratio",
    "mod_reports",
    "visited",
    "num_reports",
    "distinguished",
    "link_id",
    "replies",
    "parent_id",
    "controversiality",
    "body",
    "body_html",
    "score_hidden",
    "depth",
    "count",
    "children"
})
public class RedditPost {

    @JsonProperty("contest_mode")
    private Boolean contestMode;
    @JsonProperty("banned_by")
    
    private String bannedBy;
    @JsonProperty("media_embed")
    @Transient
    private Object mediaEmbed;
    @JsonProperty("subreddit")
    
    private String subreddit;
    @JsonProperty("selftext_html")
    
    private String selftextHtml;
    @JsonProperty("selftext")
    
    private String selftext;
    @JsonProperty("likes")
    
    private String likes;
    @JsonProperty("suggested_sort")
    
    private String suggestedSort;
    @JsonProperty("user_reports")
	@Transient
    private List<String> userReports = null;
    @JsonProperty("secure_media")
    @Transient
    private Object secureMedia;
    @JsonProperty("saved")
    private Boolean saved;
    @JsonProperty("id")
    @Id
    private String id;
    @JsonProperty("view_count")
    
    private String viewCount;
    
    @JsonProperty("secure_media_embed")
    @Transient
    @JsonIgnore
    private Object secureMediaEmbed;
    @JsonProperty("clicked")
    private Boolean clicked;
    @JsonProperty("score")
    private Integer score;
    
    @JsonProperty("report_reasons")
    private String reportReasons;
    
    @JsonProperty("author")
    private String author;
    
    @JsonProperty("link_flair_text")
    private String linkFlairText;
    
    @JsonProperty("subreddit_name_prefixed")
    private String subredditNamePrefixed;
    
    @JsonProperty("approved_by")
    private String approvedBy;
    @JsonProperty("over_18")
    private Boolean over18;
    
    @JsonProperty("domain")
    private String domain;
    @JsonProperty("hidden")
    private Boolean hidden;
    
    @JsonProperty("preview")
    @Transient
    private Object preview;
    @JsonProperty("num_comments")
    private Integer numComments;
    
    @JsonProperty("thumbnail")
    private String thumbnail;
    
    @JsonProperty("subreddit_id")
    private String subredditId;
    
    @JsonProperty("edited")
    private String edited;
    
    @JsonProperty("link_flair_css_class")
    private String linkFlairCssClass;
    
    @JsonProperty("author_flair_css_class")
    private String authorFlairCssClass;
    @JsonProperty("gilded")
    private Integer gilded;
    @JsonProperty("downs")
    private Integer downs;
    @JsonProperty("brand_safe")
    private Boolean brandSafe;
    @JsonProperty("archived")
    private Boolean archived;
    
    @JsonProperty("removal_reason")
    private String removalReason;
    
    @JsonProperty("post_hint")
    private String postHint;
    @JsonProperty("stickied")
    private Boolean stickied;
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
    
    @JsonProperty("subreddit_type")
    private String subredditType;
    @JsonProperty("locked")
    private Boolean locked;
    
    @JsonProperty("name")
    private String name;
    @JsonProperty("created")
    private Double created;
    @JsonProperty("url")
    
    private String url;
    @JsonProperty("author_flair_text")
    
    private String authorFlairText;
    @JsonProperty("quarantine")
    private Boolean quarantine;
    
    @JsonProperty("title")
    private String title;
    @JsonProperty("created_utc")
    private Double createdUtc;
    @JsonProperty("ups")
    private Integer ups;
    @JsonProperty("media")
    @Transient
    private Object media;
    @JsonProperty("upvote_ratio")
    private Double upvoteRatio;
    @JsonProperty("mod_reports")
    
    @Transient
    private List<String> modReports = null;
    @JsonProperty("visited")
    private Boolean visited;
    
    @JsonProperty("num_reports")
    private String numReports;
    
    @JsonProperty("distinguished")
    private String distinguished;
    
    @JsonProperty("link_id")
    private String linkId;
    @JsonProperty("replies")
    @Transient
    private Object replies;
    
    @JsonProperty("parent_id")
    private String parentId;
    @JsonProperty("controversiality")
    private Integer controversiality;
    @JsonProperty("body")
	@Column(columnDefinition="TEXT")
    private String body;
    @JsonProperty("body_html")
	@Column(columnDefinition="TEXT")
    private String bodyHtml;
    @JsonProperty("score_hidden")
    private Boolean scoreHidden;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("children")
	@Transient
    private List<String> children = null;
    @JsonIgnore
	@Transient
    private Map<String, String> additionalProperties = new HashMap<String, String>();
    
    @JsonIgnore
	private Double valiantScore;


    @JsonIgnore
	private Double valiantScoreDomain;
    

    @JsonIgnore
	private Double valiantScoreAuthorDomain;
    
    
    public Double getValiantScore() {
		return valiantScore;
	}

	public void setValiantScore(Double valiantScore) {
		this.valiantScore = valiantScore;
	}

	public Double getValiantScoreDomain() {
		return valiantScoreDomain;
	}

	public void setValiantScoreDomain(Double valiantScoreDomain) {
		this.valiantScoreDomain = valiantScoreDomain;
	}

	public Double getValiantScoreAuthorDomain() {
		return valiantScoreAuthorDomain;
	}

	public void setValiantScoreAuthorDomain(Double valiantScoreAuthorDomain) {
		this.valiantScoreAuthorDomain = valiantScoreAuthorDomain;
	}

	@JsonIgnore
	@Transient
    private Boolean isCorrect;
	

	@JsonIgnore
	@Transient
    private Boolean isCorrectDomain;
	

	@JsonIgnore
	@Transient
    private Boolean isCorrectAuthorDomain;
	
	@JsonIgnore
	private String threadId;

    public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	@JsonProperty("contest_mode")
    public Boolean getContestMode() {
        return contestMode;
    }

    @JsonProperty("contest_mode")
    public void setContestMode(Boolean contestMode) {
        this.contestMode = contestMode;
    }

    @JsonProperty("banned_by")
    public String getBannedBy() {
        return bannedBy;
    }

    @JsonProperty("banned_by")
    public void setBannedBy(String bannedBy) {
        this.bannedBy = bannedBy;
    }

    @JsonProperty("media_embed")
    public Object getMediaEmbed() {
        return mediaEmbed;
    }

    @JsonProperty("media_embed")
    public void setMediaEmbed(Object mediaEmbed) {
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
    public String getSelftextHtml() {
        return selftextHtml;
    }

    @JsonProperty("selftext_html")
    public void setSelftextHtml(String selftextHtml) {
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
    public String getLikes() {
        return likes;
    }

    @JsonProperty("likes")
    public void setLikes(String likes) {
        this.likes = likes;
    }

    @JsonProperty("suggested_sort")
    public String getSuggestedSort() {
        return suggestedSort;
    }

    @JsonProperty("suggested_sort")
    public void setSuggestedSort(String suggestedSort) {
        this.suggestedSort = suggestedSort;
    }

    @JsonProperty("user_reports")
    public List<String> getUserReports() {
        return userReports;
    }

    @JsonProperty("user_reports")
    public void setUserReports(List<String> userReports) {
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

    @JsonProperty("saved")
    public Boolean getSaved() {
        return saved;
    }

    @JsonProperty("saved")
    public void setSaved(Boolean saved) {
        this.saved = saved;
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
    public String getViewCount() {
        return viewCount;
    }

    @JsonProperty("view_count")
    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    @JsonProperty("secure_media_embed")
    public Object getSecureMediaEmbed() {
        return secureMediaEmbed;
    }

    @JsonProperty("secure_media_embed")
    public void setSecureMediaEmbed(Object secureMediaEmbed) {
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
    public String getReportReasons() {
        return reportReasons;
    }

    @JsonProperty("report_reasons")
    public void setReportReasons(String reportReasons) {
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

    @JsonProperty("link_flair_text")
    public String getLinkFlairText() {
        return linkFlairText;
    }

    @JsonProperty("link_flair_text")
    public void setLinkFlairText(String linkFlairText) {
        this.linkFlairText = linkFlairText;
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
    public String getApprovedBy() {
        return approvedBy;
    }

    @JsonProperty("approved_by")
    public void setApprovedBy(String approvedBy) {
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

    @JsonProperty("preview")
    public Object getPreview() {
        return preview;
    }

    @JsonProperty("preview")
    public void setPreview(Object preview) {
        this.preview = preview;
    }

    @JsonProperty("num_comments")
    public Integer getNumComments() {
        return numComments;
    }

    @JsonProperty("num_comments")
    public void setNumComments(Integer numComments) {
        this.numComments = numComments;
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
    public String getEdited() {
        return edited;
    }

    @JsonProperty("edited")
    public void setEdited(String edited) {
        this.edited = edited;
    }

    @JsonProperty("link_flair_css_class")
    public String getLinkFlairCssClass() {
        return linkFlairCssClass;
    }

    @JsonProperty("link_flair_css_class")
    public void setLinkFlairCssClass(String linkFlairCssClass) {
        this.linkFlairCssClass = linkFlairCssClass;
    }

    @JsonProperty("author_flair_css_class")
    public String getAuthorFlairCssClass() {
        return authorFlairCssClass;
    }

    @JsonProperty("author_flair_css_class")
    public void setAuthorFlairCssClass(String authorFlairCssClass) {
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
    public String getRemovalReason() {
        return removalReason;
    }

    @JsonProperty("removal_reason")
    public void setRemovalReason(String removalReason) {
        this.removalReason = removalReason;
    }

    @JsonProperty("post_hint")
    public String getPostHint() {
        return postHint;
    }

    @JsonProperty("post_hint")
    public void setPostHint(String postHint) {
        this.postHint = postHint;
    }

    @JsonProperty("stickied")
    public Boolean getStickied() {
        return stickied;
    }

    @JsonProperty("stickied")
    public void setStickied(Boolean stickied) {
        this.stickied = stickied;
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

    @JsonProperty("subreddit_type")
    public String getSubredditType() {
        return subredditType;
    }

    @JsonProperty("subreddit_type")
    public void setSubredditType(String subredditType) {
        this.subredditType = subredditType;
    }

    @JsonProperty("locked")
    public Boolean getLocked() {
        return locked;
    }

    @JsonProperty("locked")
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
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
    public String getAuthorFlairText() {
        return authorFlairText;
    }

    @JsonProperty("author_flair_text")
    public void setAuthorFlairText(String authorFlairText) {
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

    @JsonProperty("ups")
    public Integer getUps() {
        return ups;
    }

    @JsonProperty("ups")
    public void setUps(Integer ups) {
        this.ups = ups;
    }

    @JsonProperty("media")
    public Object getMedia() {
        return media;
    }

    @JsonProperty("media")
    public void setMedia(Object media) {
        this.media = media;
    }

    @JsonProperty("upvote_ratio")
    public Double getUpvoteRatio() {
        return upvoteRatio;
    }

    @JsonProperty("upvote_ratio")
    public void setUpvoteRatio(Double upvoteRatio) {
        this.upvoteRatio = upvoteRatio;
    }

    @JsonProperty("mod_reports")
    public List<String> getModReports() {
        return modReports;
    }

    @JsonProperty("mod_reports")
    public void setModReports(List<String> modReports) {
        this.modReports = modReports;
    }

    @JsonProperty("visited")
    public Boolean getVisited() {
        return visited;
    }

    @JsonProperty("visited")
    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    @JsonProperty("num_reports")
    public String getNumReports() {
        return numReports;
    }

    @JsonProperty("num_reports")
    public void setNumReports(String numReports) {
        this.numReports = numReports;
    }

    @JsonProperty("distinguished")
    public String getDistinguished() {
        return distinguished;
    }

    @JsonProperty("distinguished")
    public void setDistinguished(String distinguished) {
        this.distinguished = distinguished;
    }

    @JsonProperty("link_id")
    public String getLinkId() {
        return linkId;
    }

    @JsonProperty("link_id")
    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    @JsonProperty("replies")
    public Object getReplies() {
        return replies;
    }

    @JsonProperty("replies")
    public void setReplies(Object replies) {
        this.replies = replies;
    }

    @JsonProperty("parent_id")
    public String getParentId() {
        return parentId;
    }

    @JsonProperty("parent_id")
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @JsonProperty("controversiality")
    public Integer getControversiality() {
        return controversiality;
    }

    @JsonProperty("controversiality")
    public void setControversiality(Integer controversiality) {
        this.controversiality = controversiality;
    }

    @JsonProperty("body")
    public String getBody() {
        return body;
    }

    @JsonProperty("body")
    public void setBody(String body) {
        this.body = body;
    }

    @JsonProperty("body_html")
    public String getBodyHtml() {
        return bodyHtml;
    }

    @JsonProperty("body_html")
    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    @JsonProperty("score_hidden")
    public Boolean getScoreHidden() {
        return scoreHidden;
    }

    @JsonProperty("score_hidden")
    public void setScoreHidden(Boolean scoreHidden) {
        this.scoreHidden = scoreHidden;
    }

    @JsonProperty("depth")
    public Integer getDepth() {
        return depth;
    }

    @JsonProperty("depth")
    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("children")
    public List<String> getChildren() {
        return children;
    }

    @JsonProperty("children")
    public void setChildren(List<String> children) {
        this.children = children;
    }

    @JsonAnyGetter
    public Map<String, String> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, String value) {
        this.additionalProperties.put(name, value);
    }

}
