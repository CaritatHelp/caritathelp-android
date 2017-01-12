package com.eip.red.caritathelp.Presenters.Organisation.Members;

/**
 * Created by pierr on 11/03/2016.
 */

public interface IOrganisationMembersPresenter {

    void getMembers();

    boolean isOwner();

    void upgrade(int id, String rights, int position);

    void kick(int id, int position);
}
