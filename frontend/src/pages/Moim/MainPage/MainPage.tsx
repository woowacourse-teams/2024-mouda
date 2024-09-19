import * as S from './MainPage.style';

import { Fragment, useEffect, useMemo, useState } from 'react';
import MoimTabBar, {
  MainPageTab,
} from '@_pages/Moim/MainPage/components/MoimTabBar/MoimTabBar';
import OptionsPanel, {
  OptionsPanelOption,
} from '@_components/OptionsPanel/OptionsPanel';
import {
  getLastDarakbangId,
  setLastDarakbangId,
} from '@_common/lastDarakbangManager';

import Button from '@_components/Button/Button';
import DarakbangNameWrapper from '@_components/DarakbangNameWrapper/DarakbangNameWrapper';
import GET_ROUTES from '@_common/getRoutes';
import HomeLayout from '@_layouts/HomeLayout.tsx/HomeLayout';
import HomeMainContent from './components/HomeMainContent/HomeMainContent';
import Modal from '@_components/Modal/Modal';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import Notification from '@_common/assets/notification.svg';
import PlusButton from '@_components/PlusButton/PlusButton';
import ROUTES from '@_constants/routes';
import RefreshButton from '@_components/RefreshButton/RefreshButton';
import SolidArrow from '@_components/Icons/SolidArrow';
import { common } from '@_common/common.style';
import { removeToken } from '@_utils/tokenManager';
import { requestPermission } from '@_service/notification';
import useMyDarakbangs from '@_hooks/queries/useMyDarakbang';
import useMyRoleInDarakbang from '@_hooks/queries/useMyDarakbangRole';
import { useNavigate } from 'react-router-dom';
import useNowDarakbangName from '@_hooks/queries/useNowDarakbangNameById';
import useServeToken from '@_hooks/mutaions/useServeToken';
import { useTheme } from '@emotion/react';

export default function MainPage() {
  const navigate = useNavigate();
  const { mutate } = useServeToken();
  const theme = useTheme();
  const [currentTab, setCurrentTab] = useState<MainPageTab>('모임목록');
  const [isDarakbangMenuOpened, setIsDarakbangMenuOpened] = useState(false);

  const { myDarakbangs, isLoading: isMyDarakbangLoading } = useMyDarakbangs();

  const { darakbangName = '' } = useNowDarakbangName();
  const nowDarakbangId = getLastDarakbangId();
  const { myRoleInDarakbang: myRoleInNowDarakbang } = useMyRoleInDarakbang();

  const [isModalOpen, setIsModalOpen] = useState(false);
  useEffect(() => {
    if (window.Notification && window.Notification.permission === 'default') {
      setIsModalOpen(true);
    }
  }, []);

  const handleModalClose = () => {
    setIsModalOpen(false);
  };
  const handleTabClick = (tab: MainPageTab) => {
    setCurrentTab(tab);
  };

  const handleNotification = () => {
    navigate(GET_ROUTES.nowDarakbang.notification());
    requestPermission(mutate);
  };

  const darakbangMenuOption = useMemo(() => {
    if (isMyDarakbangLoading) return [];
    const options: OptionsPanelOption[] =
      myDarakbangs?.map(({ name, darakbangId }) => {
        return {
          onClick: () => {
            setLastDarakbangId(darakbangId);
            navigate(GET_ROUTES.nowDarakbang.main());
          },
          description:
            name + (darakbangId === nowDarakbangId ? ' (현재 다락방)' : ''),
        };
      }) || [];

    options.push(
      {
        onClick: () => navigate(ROUTES.darakbangEntrance),
        description: '코드로 다른 다락방 들어가기',
        hasTopBorder: true,
      },
      {
        onClick: () => navigate(ROUTES.darakbangCreation),
        description: '다른 다락방 만들기',
        hasTopBorder: false,
      },
    );

    if (myRoleInNowDarakbang === 'MANAGER') {
      options.push(
        {
          onClick: () =>
            navigate(GET_ROUTES.nowDarakbang.darakbangManagement()),
          description: '다락방 관리하기',
          hasTopBorder: true,
        },
        {
          onClick: () =>
            navigate(GET_ROUTES.nowDarakbang.darakbangInvitation()),
          description: '다락방 공유하기',
        },
      );
    }

    options.push({
      onClick: () => {
        removeToken();
        navigate(ROUTES.main);
      },
      description: '로그아웃',
      hasTopBorder: true,
    });

    return options;
  }, [
    isMyDarakbangLoading,
    myDarakbangs,
    myRoleInNowDarakbang,
    nowDarakbangId,
    navigate,
  ]);

  const darakbangMenu = useMemo(() => {
    return (
      <OptionsPanel
        options={darakbangMenuOption}
        onClose={() => setIsDarakbangMenuOpened(false)}
        onAfterSelect={() => setIsDarakbangMenuOpened(false)}
        movedHeight="5rem"
        movedWidth="1.8rem"
        width="80%"
        maxHeight="50vh"
      />
    );
  }, [darakbangMenuOption]);
  return (
    <Fragment>
      <HomeLayout>
        <HomeLayout.Header>
          <HomeLayout.Header.Top>
            <HomeLayout.Header.Top.Left>
              <div
                css={[S.headerLeft, common.cursorPointer, common.nonScroll]}
                onClick={(e) => {
                  e.stopPropagation();
                  setIsDarakbangMenuOpened(!isDarakbangMenuOpened);
                }}
              >
                <DarakbangNameWrapper>{darakbangName}</DarakbangNameWrapper>
                <SolidArrow
                  direction={isDarakbangMenuOpened ? 'up' : 'down'}
                  width="15"
                  height="15"
                />
              </div>
            </HomeLayout.Header.Top.Left>

            <HomeLayout.Header.Top.Right>
              <button css={S.headerButton} onClick={handleNotification}>
                <Notification />
              </button>
              <RefreshButton />
            </HomeLayout.Header.Top.Right>
          </HomeLayout.Header.Top>
          {isDarakbangMenuOpened && darakbangMenu}
          <MoimTabBar currentTab={currentTab} onTabClick={handleTabClick} />
        </HomeLayout.Header>

        <HomeLayout.Main>
          <HomeMainContent currentTab={currentTab} />
        </HomeLayout.Main>

        <HomeLayout.HomeFixedButtonWrapper>
          <PlusButton
            onClick={() => navigate(GET_ROUTES.nowDarakbang.addMoim())}
          />
        </HomeLayout.HomeFixedButtonWrapper>
      </HomeLayout>

      <NavigationBarWrapper>
        <NavigationBar />
        {isModalOpen && (
          <Modal onClose={handleModalClose}>
            <div css={S.ModalContent({ theme })}>
              알림을 허용하시면 모임에 대한 알림을 받을 수 있습니다.
              <br />
              우측 상단의 알림 버튼을 눌러주세요.
            </div>
            <Button shape="bar" onClick={handleModalClose}>
              닫기
            </Button>
          </Modal>
        )}
      </NavigationBarWrapper>
    </Fragment>
  );
}
