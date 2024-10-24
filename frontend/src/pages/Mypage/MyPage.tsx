import * as S from './MyPage.style';

import { Fragment, useEffect } from 'react';
import { css, useTheme } from '@emotion/react';

import Edit from '@_common/assets/edit.svg';
import GET_ROUTES from '@_common/getRoutes';
import InformationLayout from '@_layouts/InformationLayout/InformationLayout';
import MineInfoCard from './components/MineInfoCard/MineInfoCard';
import MyInfoTabBar from './components/MyInfoTabBar/MyInfoTabBar';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import Setting from '@_common/assets/setting.svg';
import { common } from '@_common/common.style';
import useMyPage from './hook/useMyPage';
import { useNavigate } from 'react-router-dom';

export default function MyPage() {
  const navigate = useNavigate();
  const theme = useTheme();

  const {
    myInfo,
    fileInput,
    profile,
    nickname,
    description,
    isEditing,
    isShownRest,
    isValidMyInfo,
    isImageLoading,
    setNickname,
    setDescription,
    handleEditClick,
    onChange,
    onUpload,
    handleProfileClick,
    handleCancel,
    handleDefaultProfile,
  } = useMyPage();
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    <Fragment>
      <InformationLayout>
        <InformationLayout.Header>
          <InformationLayout.Header.Left>
            <span css={[[theme.typography.h5, common.nonDrag]]}>
              마이페이지
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
                {isShownRest && (
                  <button
                    css={S.AccountButton({ theme })}
                    onClick={handleDefaultProfile}
                  >
                    기본이미지로 변경
                  </button>
                )}
                {isValidMyInfo && (
                  <button
                    css={S.AccountButton({ theme })}
                    onClick={() => {
                      if (!isImageLoading) {
                        onUpload();
                      }
                    }}
                    disabled={isImageLoading}
                  >
                    {isImageLoading ? '저장 중...' : '저장'}
                  </button>
                )}
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
                  profile,
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
      </InformationLayout>
      <NavigationBarWrapper>
        <NavigationBar />
      </NavigationBarWrapper>
    </Fragment>
  );
}
