import * as S from './MainPage.style';

import { Fragment, useMemo, useState } from 'react';
import MoimTabBar, { MainPageTab } from '@_components/MoimTabBar/MoimTabBar';
import OptionsPanel, {
  OptionsPanelOption,
} from '@_components/OptionsPanel/OptionsPanel';
import {
  getLastDarakbangId,
  setLastDarakbangId,
} from '@_common/lastDarakbangManager';

import GET_ROUTES from '@_common/getRoutes';
import HomeLayout from '@_layouts/HomeLayout.tsx/HomeLayout';
import HomeMainContent from '@_components/HomeMainContent/HomeMainContent';
import NavigationBar from '@_components/NavigationBar/NavigationBar';
import NavigationBarWrapper from '@_layouts/components/NavigationBarWrapper/NavigationBarWrapper';
import Notification from '@_common/assets/notification.svg';
import PlusButton from '@_components/PlusButton/PlusButton';
import ROUTES from '@_constants/routes';
import SolidArrow from '@_components/Icons/SolidArrow';
import { removeToken } from '@_utils/tokenManager';
import { requestPermission } from '@_service/notification';
import useMyDarakbangs from '@_hooks/queries/useMyDarakbang';
import useMyRoleInDarakbang from '@_hooks/queries/useMyDarakbangRole';
import { useNavigate } from 'react-router-dom';
import useNowDarakbangName from '@_hooks/queries/useNowDarakbangNameById';
import useServeToken from '@_hooks/mutaions/useServeToken';

export default function MainPage() {
  const navigate = useNavigate();
  const { mutate } = useServeToken();
  const [currentTab, setCurrentTab] = useState<MainPageTab>('모임목록');
  const [isDarakbangMenuOpened, setIsDarakbangMenuOpened] = useState(false);

  const { myDarakbangs, isLoading: isMyDarakbangLoading } = useMyDarakbangs();

  const { darakbangName = '' } = useNowDarakbangName();
  const nowDarakbangId = getLastDarakbangId();
  const { myRoleInDarakbang: myRoleInNowDarakbang } = useMyRoleInDarakbang();

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
            name + (darakbangId === nowDarakbangId ? '(현재 다락방)' : ''),
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
        movedWidth="3rem"
        width="80%"
      />
    );
  }, [darakbangMenuOption]);
  return (
    <Fragment>
      <HomeLayout>
        <HomeLayout.Header>
          <HomeLayout.Header.Top>
            <HomeLayout.Header.Top.Left>
              <div css={S.headerLeft}>
                {darakbangName}
                <SolidArrow
                  direction={isDarakbangMenuOpened ? 'up' : 'down'}
                  onClick={(e) => {
                    e.stopPropagation();
                    setIsDarakbangMenuOpened(!isDarakbangMenuOpened);
                  }}
                  width="15"
                  height="15"
                />
              </div>
            </HomeLayout.Header.Top.Left>

            <HomeLayout.Header.Top.Right>
              <button css={S.headerButton} onClick={handleNotification}>
                <Notification />
              </button>
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
      </NavigationBarWrapper>
    </Fragment>
  );
}
