package com.wk.JavaTCPIPSOCKET.voteDemo;

/**
 * Created by wkhua on 16/12/22.
 */
public class VoteMsg {
    private boolean isInquiry;// true if inquiry; false if vote
    private boolean isResponse;// true if response from server; false if request from client
    private int candidateID;// in[0,1000]
    private long voteCount; // nonzero only in response
    public static final int MAX_CANDIDATE_ID = 1000;

    public void VoteMsg(boolean isResponse, boolean isInquiry, int candidateID, long voteCount) throws IllegalArgumentException{
        if(voteCount != 0 && !isResponse){//在投票的时候voteCount必须为零
            throw new IllegalArgumentException("Request vote count must be zero");
        }
        if(candidateID<0 || candidateID > MAX_CANDIDATE_ID){
            throw new IllegalArgumentException("Bad Candidate ID:"+candidateID);
        }
        if( voteCount <0 ){
            throw new IllegalArgumentException("Total must be larger than zero");
        }
        this.candidateID = candidateID;
        this.isResponse = isResponse;
        this.isInquiry = isInquiry;
        this.voteCount = voteCount;

    }

    public boolean isInquiry() {
        return isInquiry;
    }

    public void setInquiry(boolean inquiry) {
        isInquiry = inquiry;
    }

    public boolean isResponse() {
        return isResponse;
    }

    public void setResponse(boolean response) {
        isResponse = response;
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) throws IllegalArgumentException {
        if(candidateID <0 || candidateID > MAX_CANDIDATE_ID){
            throw new IllegalArgumentException("Bad Candidate ID:"+candidateID);
        }
        this.candidateID = candidateID;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        if((voteCount != 0 && !isResponse) ||voteCount< 0){//在投票的时候voteCount必须为零
            throw new IllegalArgumentException("Bad vote count ");
        }
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        String res = (isInquiry ? "inquery" : "vote")+ " for candidate "+candidateID;
        if(isResponse){
            res = "response to "+res+" who now has "+voteCount+"vote(s)";
        }
        return res;
    }
}
