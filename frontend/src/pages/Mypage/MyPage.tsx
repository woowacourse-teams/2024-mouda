// import { useRef } from 'react';
import { css, useTheme } from '@emotion/react';
import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import MineInfoCard from './components/MineInfoCard/MineInfoCard';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import { common } from '@_common/common.style';
import useMyInfo from '@_hooks/queries/useMyInfo';
import useNowDarakbangName from '@_hooks/queries/useNowDarakbangNameById';
import * as S from './MyPage.style';
import MyInfoTabBar from './components/MyInfoTabBar/MyInfoTabBar';
import Setting from '@_common/assets/setting.svg';
import Edit from '@_common/assets/edit.svg';
import { ChangeEvent, useEffect, useRef, useState } from 'react'; // ChangeEvent를 가져옴
import useEditMyInfo from '@_hooks/mutaions/useEditMyInfo';
import { useNavigate } from 'react-router-dom';
import GET_ROUTES from '@_common/getRoutes';

export default function MyPage() {
  const navigate = useNavigate();
  const { myInfo } = useMyInfo();
  const { darakbangName } = useNowDarakbangName();
  const fileInput = useRef<HTMLInputElement | null>(null); // 타입을 명시적으로 지정
  const [profile, setProfile] = useState(myInfo?.profile || '');
  const [nickname, setNickname] = useState(myInfo?.nickname || '');
  const [description, setDescription] = useState(myInfo?.description || '');
  const [selectedFile, setSelectedFile] = useState<File | string>('');
  const [isEditing, setIsEditing] = useState(false); // 편집 모드 상태
  const [isRest, setIsRest] = useState('false');

  const theme = useTheme();
  const { mutate } = useEditMyInfo();

  useEffect(() => {
    if (myInfo) {
      setNickname(myInfo.nickname || '');
      setDescription(myInfo.description || '');
      setProfile(myInfo.profile || '');
      setSelectedFile(myInfo.profile || '');
    }
  }, [myInfo]); // myInfo가 업데이트될 때마다 상태 업데이트

  const handleEditClick = () => {
    setIsEditing((prev) => !prev); // 편집 모드 활성화
  };

  const onChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      setSelectedFile(e.target.files[0]); // 선택한 파일을 상태에 저장
      setIsRest('false');
    } else {
      setProfile(myInfo?.profile ?? '');
      return;
    }

    const reader = new FileReader();
    reader.onload = () => {
      if (reader.readyState === 2 && typeof reader.result === 'string') {
        setProfile(reader.result);
      }
    };
    reader.readAsDataURL(e.target.files[0]);
  };

  const onUpload = async () => {
    const formData = new FormData();

    // 파일 추가

    formData.append('file', selectedFile);
    // 문자열 데이터 추가
    formData.append('nickname', nickname ?? '');
    formData.append('description', description ?? '');
    formData.append('isDefault', isRest);

    try {
      // 서버로 파일 및 데이터 전송
      mutate(formData); // FormData 객체 자체를 전달
      setProfile('');
      handleEditClick(); // 편집 모드 비활성화
    } catch (error) {
      console.error('파일 업로드 실패', error);
    }
  };

  const handleProfileClick = () => {
    fileInput.current?.click(); // ProfileFrame 클릭 시 파일 선택창 열기
  };

  const handleCancel = () => {
    // 편집 취소시 기존 데이터 복구
    setProfile(myInfo?.profile || '');
    setNickname(myInfo?.nickname || '');
    setDescription(myInfo?.description || '');
    setIsEditing(false);
    setIsRest('false');
  };
  const handleDefaultProfile = () => {
    setProfile('');
    setSelectedFile('');
    setIsRest('true');
  };

  return (
    <InformationLayout>
      <InformationLayout.Header>
        <InformationLayout.Header.Left>
          <span css={[[theme.typography.h5, common.nonDrag]]}>
            {darakbangName}
          </span>
        </InformationLayout.Header.Left>
        <InformationLayout.Header.Right>
          {!isEditing ? (
            <>
              <button
                css={S.AccountButton({ theme })}
                onClick={handleEditClick}
              >
                <Edit />
              </button>
              <button
                css={S.AccountButton({ theme })}
                onClick={() => navigate(GET_ROUTES.nowDarakbang.setting())}
              >
                <Setting />
              </button>
            </>
          ) : (
            <>
              <button
                css={S.AccountButton({ theme })}
                onClick={handleDefaultProfile}
              >
                기본이미지로 변경
              </button>
              <button css={S.AccountButton({ theme })} onClick={onUpload}>
                저장
              </button>
              <button css={S.AccountButton({ theme })} onClick={handleCancel}>
                취소
              </button>
            </>
          )}
        </InformationLayout.Header.Right>
      </InformationLayout.Header>
      <InformationLayout.ContentContainer>
        <section css={S.mainContainer}>
          {myInfo && (
            <MineInfoCard
              myInfo={{
                nickname,
                profile: profile,
                name: myInfo.name,
              }}
              onProfileClick={handleProfileClick}
              isEditing={isEditing}
              setNickname={setNickname}
            />
          )}
          <MyInfoTabBar
            description={description}
            isEditing={isEditing}
            setDescription={setDescription}
          />
          <div
            css={css`
              display: flex;
              gap: 10px;
              justify-content: end;
            `}
          >
            <input
              type="file"
              accept="image/jpg,image/png,image/jpeg"
              name="profile_img"
              onChange={onChange}
              ref={fileInput}
              style={{ display: 'none' }}
            />
          </div>
        </section>
      </InformationLayout.ContentContainer>
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </InformationLayout>
  );
}
