import ProfileBox from '@_components/Profile/ProfileBox';
import * as S from './ProfileList.style';
import EmptyProfile from '@_common/assets/empty_profile.svg';

export default function ProfileList() {
  //TODO 데이터 타입이 정해질 경우 수정 예정
  return (
    <div css={S.ProfileContanier}>
      <ProfileBox name="치코" src={EmptyProfile}></ProfileBox>
      <ProfileBox name="치코" src={EmptyProfile}></ProfileBox>
      <ProfileBox name="치코" src={EmptyProfile}></ProfileBox>
      <ProfileBox name="치코" src={EmptyProfile}></ProfileBox>
      <ProfileBox name="치코" src={EmptyProfile}></ProfileBox>
      <ProfileBox name="치코" src={EmptyProfile}></ProfileBox>
      <ProfileBox name="치코" src={EmptyProfile}></ProfileBox>
      <ProfileBox name="치코" src={EmptyProfile}></ProfileBox>
    </div>
  );
}
