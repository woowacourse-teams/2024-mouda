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
  const [image, setImage] = useState(myInfo?.profile || '');
  const [nickname, setNickname] = useState(myInfo?.nickname || '');
  const [description, setDescription] = useState(myInfo?.description || '');
  const [selectedFile, setSelectedFile] = useState<File | undefined>(undefined);
  const [isEditing, setIsEditing] = useState(false); // 편집 모드 상태

  const theme = useTheme();
  const { mutate } = useEditMyInfo();

  useEffect(() => {
    if (myInfo) {
      setNickname(myInfo.nickname || '');
      setDescription(myInfo.description || '');
      setImage(myInfo.profile || '');
    }
  }, [myInfo]); // myInfo가 업데이트될 때마다 상태 업데이트

  const handleEditClick = () => {
    setIsEditing((prev) => !prev); // 편집 모드 활성화
  };

  const onChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      setSelectedFile(e.target.files[0]); // 선택한 파일을 상태에 저장
    } else {
      setImage(
        'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png',
      );
      return;
    }

    const reader = new FileReader();
    reader.onload = () => {
      if (reader.readyState === 2 && typeof reader.result === 'string') {
        setImage(reader.result);
      }
    };
    reader.readAsDataURL(e.target.files[0]);
  };

  const onUpload = async () => {
    if (!selectedFile) return;

    const formData = new FormData();

    // 파일 추가
    formData.append('profile_img', selectedFile);
    // 문자열 데이터 추가
    // formData.append('nickname', nickname);
    // formData.append('description', description);

    try {
      // 서버로 파일 및 데이터 전송
      mutate(formData); // FormData 객체 자체를 전달
      setImage('');
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
    setImage(myInfo?.profile || '');
    setNickname(myInfo?.nickname || '');
    setDescription(myInfo?.description || '');
    setIsEditing(false);
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
                profile: image,
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
