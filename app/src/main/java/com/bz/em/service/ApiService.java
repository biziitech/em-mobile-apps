package com.bz.em.service;

import com.bz.em.model.DueLoanCollectionData;
import com.bz.em.model.InspectionMachineResult;
import com.bz.em.model.InspectionMachineResultDTO;
import com.bz.em.model.InstallmentInfo;
import com.bz.em.model.LoanCollectionInfo;
import com.bz.em.model.LoanInfo;
import com.bz.em.model.LoanInspectionData;
import com.bz.em.model.SamityInspectionMachineResultDTO;
import com.bz.em.model.SamityMemberData;
import com.bz.em.model.SamityMemberMachineDetailData;
import com.bz.em.model.Somity;
import com.bz.em.model.SomityInspection;
import com.bz.em.model.SomityMemberDetail;
import com.bz.em.model.TatInfo;
import com.bz.em.model.User;
import com.bz.em.model.UserDtl;
import com.bz.em.model.UserProfile;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Bellal Hossain
 **/

public interface ApiService {

    @POST("users/")
    Call<List<User>> getUserData(@Body User user);

    @GET("user-details")
    Call<UserDtl> userLogin(
            @Query("username") String username,
            @Query("password") String password
    );


    //@GET("loan-applications-list/")
    @GET("loan-applications-list-by-samity/")
    Call<List<SomityMemberDetail>> getSomityMemberList(
            @Query("samityId") Long somityId,
            @Query("inspectorId") Long inspectorId
    );

    /* user details */
    @GET("users/{userId}/")
    Call<UserProfile> getUserDtl(
            @Path("userId") Long userId
    );

    /*loan info*/
    @GET("loan-weaver-data/{weaverId}/")
    Call<LoanInfo> getLoanInfo(
            @Path("weaverId") Long weaverId
    );

    /*get installment info info*/
    @GET("loan-installments/{loanApplicationId}/")
    Call<InstallmentInfo> getInstallmentInfo(
            @Path("loanApplicationId") Long loanApplicationId
    );


    @GET("loan-member-machine-detail/{loanApplicationId}/")
    Call<List<TatInfo>> getTatMachineInfo(
            @Path("loanApplicationId") Long loanApplicationId
    );

    /* get samity list by inspector */
    @GET("loan-samity-list-by-inspector/")
    Call<List<Somity>> getSomityList(
            @Query("inspectorId") Long inspectorId
    );

    /*@GET("samity-inspection-samity-list/{basicCenterId}")
    Call<List<Somity>> getInspectionSomityList(
            @Path("basicCenterId") Long basicCenterId
    );
    */

    /*loan inspection get submitted image*/
    @GET("loan-inspection-images/{loanApplicationId}")
    Call<List<String>> getSubmittedPhotos(
            @Path("loanApplicationId") long loanApplicationId
    );

    // @FormUrlEncoded
    /*post loan inspection photo data*/
    @Multipart
    @POST("submit-inspection-photo-data/")
    Call<Long> saveLoanInspectionImg(

            @Query("loanAccountId") Long loanAccountId,
            @Query("longitude") Double longitude,
            @Query("latitude") Double latitude,
            @Query("location") String location,
            @Query("remarks") String remarks,
            @Part MultipartBody.Part filePart
    );

    /*post loan inspection machine data*/

    @POST("submit-inspection-machine-data/")
    Call <List<Long>> saveMachineInspectionResult(
            @Body InspectionMachineResultDTO inspectionMachineResultDTO);

    @POST("loan-repayment/{loanAccountId}")
    Call<Object> postLoanRepayment(
            @Path("loanAccountId") long loanAccountId,
            @Query("collectionAmount") double collectionAmount
    );

    @POST("loan-finalize-inspection/")
    Call<Object> postFinalizeFlag(
            @Query("loanApplicationId") Long loanApplicationId
    );

    @POST("samity-finalize-inspection/")
    Call<Object> postSamityFinalizeFlag(
            @Query("samityId") Long samityId
    );


    @GET("loan-accounts/{basicCenterId}/{samityMemberId}")
    Call<LoanCollectionInfo> loanAcId(
            @Path("basicCenterId") long basicCenterId,
            @Path("samityMemberId") long samityMemberId
    );

    /* get due collection */
    @GET("loan-account/{loanAccountId}/due-installments")
    Call<DueLoanCollectionData> getDueCollectionList(
            @Path("loanAccountId") long loanAccountId
    );

    /*get repayment samity list*/
    @GET("repayment-samity-list/")
    Call<List<Somity>> getRepaymentSomityList();

    /*get repayment samity member lis*/
    @GET("repayment-applications-list-by-samity/")
    Call<List<SomityMemberDetail>> getRepaymentSamityMemberList(
            @Query("samityId") long samityId
    );

    /*get somity inspection samity list*/
    @GET("samity-inspection-samity-list/")
    Call<List<Somity>> getSamityInspectionSomityList(
            @Query("inspectorId") Long inspectorId
    );

    /*get samity inspection samity member list*/
    @GET("samity-inspection-member-list/")
    Call<List<SamityMemberData>> getSamityInspectionSamityMemberList(
            @Query("samityId") long samityId,
            @Query("inspectorId") long inspectorId
    );

    /*post samity inspection machine data*/
    @POST("submit-samity-inspection-machine-data/")
    Call<Long> saveSamityInspectionMachineData(
            @Body SamityInspectionMachineResultDTO dto);

    /*post samity inspection photo data*/
    @Multipart
    @POST("submit-samity-inspection-photo-data/")
    Call<Long> saveSamityInspectionImg(
            @Query("memberId") Long memberId,
            @Query("longitude") Double longitude,
            @Query("latitude") Double latitude,
            @Query("location") String location,
            @Query("remarks") String remarks,
            @Part MultipartBody.Part filePart
    );

    ///samity-member-machine-detail/{memberId}

    @GET("samity-member-machine-detail/{memberId}/")
    Call<List<SamityMemberMachineDetailData>> getSamityMachineInfo(
            @Path("memberId") Long memberId
    );
    /*samity inspection get submitted image*/
    @GET("samity-member-inspection-images/{memberId}")
    Call<List<String>> getSamitySubmittedPhotos(
            @Path("memberId") long memberId
    );
}